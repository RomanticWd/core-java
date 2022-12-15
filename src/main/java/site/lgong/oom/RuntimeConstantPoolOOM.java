package site.lgong.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * 运行时常量池导致的内存溢出异常
 * JDK1.6及以前
 *      VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M,
 *      其中 PermSize方法区 (永久代) 初始大小，XX:MaxPermSize方法区 (永久代) 最大大小,超过这个值将会抛出 OutOfMemoryError 异常:java.lang.OutOfMemoryError: PermGen
 *
 * JDK1.7后
 *      VM Args: -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M,
 *      其中：MetaspaceSize设置元空间初始（和最小大小），MaxMetaspaceSize 设置 Metaspace 的最大大小
 *
 * JDK1.7 字符串常量池和静态变量从永久代移动了 Java 堆中，所以再设置永久代大小或者元空间大小，以下代码都会一直while循环下去。
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args) {
        // 使用List保持着常量池的引用，避免Full GC回收常量池的行为
        List<String> list = new ArrayList<>();
        // 10mb的PermSize在integer的范围内足够产生OOM了
        int i = 0;
//        while (true) {
//            //intern是一个native方法，如果字符串常量池有此字符串，则返回string对象，否则将此string对象加入常量池。
//            list.add(String.valueOf(i++).intern());
//        }

        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        // 这里的java在代码运行前已经非显式的加入字符串常量池中了
        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }
}
