package site.lgong.thread;

/**
 * 死锁示例
 *
 * @author yue.liu
 * @since [2021/8/13 15:14]
 */
public class DeadLockDemo {

    private static Object resource1 = new Object();
    private static Object resource2 = new Object();

    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (resource1) {
                System.out.println(Thread.currentThread().getName() + "resource1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resource2");
                synchronized (resource2) {
                    System.out.println(Thread.currentThread() + "get resource2");
                }
            }
        }, "线程1").start();

        new Thread(() -> {
            synchronized (resource1) {
                System.out.println(Thread.currentThread().getName() + "resource1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resource2");
                synchronized (resource2) {
                    System.out.println(Thread.currentThread() + "get resource2");
                }
            }
        }, "线程2").start();

    }

}
