package site.lgong.oom;

/**
 * 虚拟机栈和本地方法栈的OOM测试：StackOverflowError
 * VM Args: -Xss256k
 * -Xss设置栈内存容量大小。
 * 单线程下：如果栈内存容量够大，那么单个线程的栈深度也会随之变大。
 * 多线程下：栈内存的大小=操作系统分配给进程的内存 - Xmx（最大堆容量）- MaxPermSize（最大方法区容量），栈内存大小基本固定，栈内存越大，可以创建的线程数量就越少。
 * 栈的内存大小不允许动态扩展，如果线程请求的栈深度大于虚拟机所允许的最大深度，将抛出StackOverflowError异常
 */
public class JavaVMStackSOF {

    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();;
    }

    public static void main(String[] args) {
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }

        // 执行结果：
//        stack length:2961
//        Exception in thread "main" java.lang.StackOverflowError
//        at site.lgong.oom.JavaVMStackSOF.stackLeak(JavaVMStackSOF.java:14)
//        at site.lgong.oom.JavaVMStackSOF.stackLeak(JavaVMStackSOF.java:14)

    }
}
