package site.lgong.oom;

/**
 * 虚拟机栈和本地方法栈的OOM测试：OutOfMemoryError
 * VM Args: -Xss2M
 * -Xss设置栈内存容量大小。
 * 创建线程导致内存溢出异常，解决方法：
 * 1. 减少线程数
 * 2. 更换64位虚拟机
 * 3. 减少最大堆和减少栈容量换取更多线程
 *
 * 如果栈的内存大小可以动态扩展， 如果虚拟机在动态扩展栈时无法申请到足够的内存空间，则抛出OutOfMemoryError异常
 */
public class JavaVMStackOOM {

    private void dontStop() {
        while (true) {

        }
    }

    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
//        oom.stackLeakByThread();

        // 执行结果：
        // unable to create new native thread
    }
}
