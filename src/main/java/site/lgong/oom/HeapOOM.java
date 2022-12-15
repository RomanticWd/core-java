package site.lgong.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆内存的OOM异常：HeapDumpOnOutOfMemoryError
 * VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError,
 * 其中-XX:+HeapDumpOnOutOfMemoryError用于内存溢出时候保存堆快照
 * -Xms20m -Xmx20m 限制堆内存大小为20m
 * 处理手段：通过内存映像分析工具分析快照，确认是内存泄露还是内存溢出。
 */
public class HeapOOM {

    static class OOMObject {}

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();

        while (true) {
            list.add(new OOMObject());
        }

        // 执行结果：
//        java.lang.OutOfMemoryError: Java heap space
//        Dumping heap to java_pid30660.hprof ...
//        Heap dump file created [29935071 bytes in 0.078 secs]
    }
}
