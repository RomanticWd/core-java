package site.lgong.io.socket;

import lombok.extern.slf4j.Slf4j;
import site.lgong.entity.Student;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 基于socket实现通信的客户端
 */
@Slf4j
public class SocketHelloClient {

    public Object send(Student student, String host, int port) {
        //1. 创建Socket对象并且指定服务器的地址和端口号
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            //2.通过输出流向服务器端发送请求信息
            objectOutputStream.writeObject(student);
            //3.通过输入流获取服务器响应的信息
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            // 4. 返回服务器响应信息
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("SocketHelloClient send exception:", e);
        }
        return null;
    }

    public static void main(String[] args) {
        SocketHelloClient helloClient = new SocketHelloClient();
        Student student = new Student();
        student.setName("jack");
        // 发送后获取服务端返回的对象，这时候年龄已经变了
        student = (Student) helloClient.send(student, "127.0.0.1", 6666);
        System.out.println("client receive message:" + student.getAge());
    }

}
