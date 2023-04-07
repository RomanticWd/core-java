package site.lgong.thread;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 几种ThreadLocal测试，参考博客：<a href="https://juejin.cn/post/6998552093795549191">地址</a>
 */
public class ThreadLocalTest {

    public static void main(String[] args) throws InterruptedException {

        // threadLocal测试
        jdkThreadLocalTest();
        System.out.println("============");

        //inheritableThreadLocal测试
        inheritableThreadLocalTest();
        System.out.println("============");

        //transmittableThreadLocalTest测试
        transmittableThreadLocalTest();
    }

    private static void transmittableThreadLocalTest() throws InterruptedException {
        TransmittableThreadLocal<String> local = new TransmittableThreadLocal<>();

        local.set("主线程");
        // 测试修改主线程的场景
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService = TtlExecutors.getTtlExecutorService(executorService);
        CountDownLatch c1 = new CountDownLatch(1);
        CountDownLatch c2 = new CountDownLatch(1);
        executorService.execute(() -> {
            System.out.println("thread1:" + local.get());
            c1.countDown();
            ;
        });
        c1.await();
        // 主线程修改值
        local.set("修改主线程");
        // 拿到的还是第一次设置的主线程的值
        executorService.execute(() -> {
            System.out.println("thread2:" + local.get());
            c2.countDown();
            ;
        });
        c2.await();
        executorService.shutdownNow();
        local.remove();
    }

    /**
     * threadLocal测试，发现在异步线程只有线程初始化的时候将主线程拷贝到子线程。
     *
     * @throws InterruptedException
     */
    private static void inheritableThreadLocalTest() throws InterruptedException {
        ThreadLocal<String> local = new InheritableThreadLocal<>();
        local.set("主线程");

        // 子线程可以拿到主线程的值
        new Thread(() -> {
            System.out.println("子线程1:" + local.get());
        }).start();
        Thread.sleep(2000);

        // 测试修改主线程的场景
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        CountDownLatch c1 = new CountDownLatch(1);
        CountDownLatch c2 = new CountDownLatch(1);
        executorService.execute(() -> {
            System.out.println("thread1:" + local.get());
            c1.countDown();
            ;
        });
        c1.await();
        // 主线程修改值
        local.set("修改主线程");
        // 拿到的还是第一次设置的主线程的值
        executorService.execute(() -> {
            System.out.println("thread2:" + local.get());
            c2.countDown();
            ;
        });
        c2.await();
        executorService.shutdownNow();
        local.remove();
    }

    /**
     * threadLocal测试，发现在异步线程中拿不到主线程的threadLocal中的值。
     */
    private static void jdkThreadLocalTest() {

        ThreadLocal<String> local = new ThreadLocal<>();
        try {
            local.set("主线程");
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            CountDownLatch c1 = new CountDownLatch(1);
            CountDownLatch c2 = new CountDownLatch(1);
            executorService.execute(() -> {
                System.out.println("thread1:" + local.get());
                c1.countDown();
                ;
            });

            c1.await();
            executorService.execute(() -> {
                System.out.println("thread2:" + local.get());
                c2.countDown();
                ;
            });
            c2.await();
            executorService.shutdownNow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            local.remove();
        }
    }

}
