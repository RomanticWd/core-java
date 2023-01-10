package site.lgong.thread;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 实现一个只有核心线程的线程池
 * <p>
 * 暂时不考虑任务执行异常情况下的处理。
 * 任务队列为无界队列。
 * 线程池容量固定为核心线程数量。
 * 暂时不考虑拒绝策略。
 */
public class CoreThreadPool implements Executor {

    private BlockingDeque<Runnable> workQueue;
    private static final AtomicInteger COUNTER = new AtomicInteger();
    private int coreSize;
    private int threadCount = 0;

    public CoreThreadPool(int coreSize) {
        this.coreSize = coreSize;
        this.workQueue = new LinkedBlockingDeque<>();
    }

    @Override
    public void execute(Runnable command) {
        if (++threadCount <= coreSize) {
            new Worker(command).start();
        } else {
            try {
                workQueue.put(command);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private class Worker extends Thread {

        private Runnable firstTask;

        public Worker(Runnable runnable) {
            super(String.format("Worker-%d", COUNTER.getAndIncrement()));
            this.firstTask = runnable;
        }

        @Override
        public void run() {
            Runnable task = this.firstTask;
            while (null != task || null != (task = getTask())) {
                try {
                    task.run();
                } finally {
                    task = null;
                }
            }
        }
    }

    private Runnable getTask() {
        try {
            return workQueue.take();
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void main(String[] args) throws Exception {

        CoreThreadPool threadPool = new CoreThreadPool(5);
        IntStream.range(0, 10).forEach(value -> threadPool.execute(() -> System.out.printf("thread:%s, value:%d%n", Thread.currentThread().getName(), value)));
        Thread.sleep(Integer.MAX_VALUE);
    }
}
