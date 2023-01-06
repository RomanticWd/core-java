package site.lgong.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

public class ThreadPoolTest {

    public static int PARK_TIME = 0;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        runTask(5);
    }

    public static void runTask(int threadNum) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                threadNum, threadNum, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100)
        );

        long start = System.currentTimeMillis();
        List<Future<?>> taskList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            taskList.add(threadPoolExecutor.submit(() -> {
                doJob();
            }));
        }
        for (Future<?> future : taskList) {
            future.get();
        }
        long time = System.currentTimeMillis() - start;
        System.out.println(threadNum + "个线程，耗时：" + time + "停顿占比" + (PARK_TIME * 100.0 / time));
        threadPoolExecutor.shutdown();
    }


    public static Long doJob() {
        long result = 0L;
        PARK_TIME = 0;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (i % 10_000_000 == 0) {
                try {
                    ++PARK_TIME;
                    // 模拟IO
                    LockSupport.parkNanos(100_000_000);
                } catch (Exception ignore) {
                }
            }
            result += i;
        }
        return result;
    }

}
