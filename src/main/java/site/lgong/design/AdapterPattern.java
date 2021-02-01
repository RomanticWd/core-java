package site.lgong.design;

import java.util.concurrent.Callable;

/**
 * 适配器模式：如果去美国，我们随身带的电器是无法直接使用的，因为美国的插座标准和中国不同，所以，我们需要一个适配器
 * 在代码中的常见表现：如果一个接口需要B接口，但是待传入的对象却是A接口，需要用一个Adapter，把这个A接口“变成”B接口
 */
public class AdapterPattern {

    public static void main(String[] args) {

        Callable<Long> callable = new Task(123450000L);
        // new Thread()需要传递的是Runnable接口的参数，所以这里需要进行一次转换。
        //一个办法是改写Task类，把实现的Callable改为Runnable，但这样做不好，
        // 因为Task很可能在其他地方作为Callable被引用，改写Task的接口，会导致其他正常工作的代码无法编译。

        /* 编译失败!
        Thread thread = new Thread(callable);
        */
        Thread thread = new Thread(new RunnableAdapter(callable));
        thread.start();

    }

    /**
     * 实现Callable接口的任务类
     */
    static class Task implements Callable<Long> {
        private long num;

        public Task(long num) {
            this.num = num;
        }

        public Long call() throws Exception {
            long r = 0;
            for (long n = 1; n <= this.num; n++) {
                r = r + n;
            }
            System.out.println("Result: " + r);
            return r;
        }
    }

    /**
     * 适配器，实现了Runnable接口
     */
    static class RunnableAdapter implements Runnable {

        // 引用待转换接口:
        private Callable<?> callable;

        //适配器的入参是Callable，通过转换，就实现了将Callable实现类转换成Runnable实现类
        public RunnableAdapter(Callable<?> callable) {
            this.callable = callable;
        }

        public void run() {
            // 将指定接口调用委托给转换接口调用:
            try {
                callable.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}


