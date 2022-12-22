package site.lgong.jvm;

/**
 * 内存分配测试
 */
public class GCTest {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        testAllocation();
    }

    /**
     * 对象优先在Eden区分配
     * <p>
     * vm 参数： -verbose:gc  -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * -Xms20M -Xmx20M 堆大小20M，-Xmn10M分配给新生代
     * -XX:+PrintGCDetails 打印内存回收日志
     * -XX:SurvivorRatio=8 新生代Eden区与一个Survivor区的空间比例是8：1
     */
    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB]; // 出现一次Minor GC
    }

    /**
     * 大对象直接进入老年代
     * <p>
     * vm 参数： -verbose:gc  -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -xx:PretenureSizeThreshold=3145728
     * -Xms20M -Xmx20M 堆大小20M，-Xmn10M分配给新生代
     * -XX:+PrintGCDetails 打印内存回收日志
     * -XX:SurvivorRatio=8 新生代Eden区与一个Survivor区的空间比例是8：1
     * -xx:PretenureSizeThreshold=3145728 大于这个设置值的对象直接在老年代分配
     */
    public static void testPreTenureSizeThreshold() {
        byte[] allocation1;
        allocation1 = new byte[4 * _1MB]; // 直接分配在老年代
    }

    /**
     * 长期存活的对象将进入老年代
     * <p>
     * vm 参数： -verbose:gc  -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1
     * -Xms20M -Xmx20M 堆大小20M，-Xmn10M分配给新生代
     * -XX:+PrintGCDetails 打印内存回收日志
     * -XX:SurvivorRatio=8 新生代Eden区与一个Survivor区的空间比例是8：1
     * -xx:MaxTenuringThreshold=1 对象晋升老年代的年龄阈值
     */
    public static void testTenuringThreshold() {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4];
        // 什么时候进入老年代取决于-XX:MaxTenuringThreshold设置
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];
    }

    /**
     * 长期存活的对象将进入老年代
     * <p>
     * vm 参数： -verbose:gc  -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -xx:MaxTenuringThreshold=15
     * -Xms20M -Xmx20M 堆大小20M，-Xmn10M分配给新生代
     * -XX:+PrintGCDetails 打印内存回收日志
     * -XX:SurvivorRatio=8 新生代Eden区与一个Survivor区的空间比例是8：1
     * -xx:MaxTenuringThreshold=1 对象晋升老年代的年龄阈值
     */
    public static void testTenuringThreshold2() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[_1MB / 4];
        allocation2 = new byte[_1MB / 4];
        // allocation1 + allocation2 大于survivor空间一半
        allocation3 = new byte[4 * _1MB];
        allocation4 = new byte[4 * _1MB];
        allocation4 = null;
        allocation4 = new byte[4 * _1MB];
    }

}
