package site.lgong.io;

import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class SendFileTest {

    public static void main(String[] args) {
        try {
            FileChannel readChannel = FileChannel.open(Paths.get(""), StandardOpenOption.READ);
            long len = readChannel.size();
            long position = readChannel.position();
            FileChannel writeChannel = FileChannel.open(Paths.get(""), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            // 数据传输
            readChannel.transferTo(position, len, writeChannel);
            readChannel.close();
            writeChannel.close();
        } catch (Exception e) {

        }
    }
}
