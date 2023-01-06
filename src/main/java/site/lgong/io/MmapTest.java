package site.lgong.io;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * java NIO 对mmap的支持
 */
public class MmapTest {

    public static void main(String[] args) {
        try {
            FileChannel readChannel = FileChannel.open(Paths.get(""), StandardOpenOption.READ);
            MappedByteBuffer mappedByteBuffer = readChannel.map(FileChannel.MapMode.READ_ONLY, 0, 1024 * 1024 * 40);
            FileChannel writeChannel = FileChannel.open(Paths.get(""), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            // 数据传输
            writeChannel.write(mappedByteBuffer);
            readChannel.close();
            writeChannel.close();
        } catch (Exception e) {

        }
    }
}
