package site.lgong.io.socket;

import lombok.extern.slf4j.Slf4j;
import site.lgong.entity.Student;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 基于socket实现通信的服务端
 */
@Slf4j
public class SocketHelloServer {

    public void start(int port) {
        //1.创建 ServerSocket 对象并且绑定一个端口
        try (ServerSocket server = new ServerSocket(port)) {
            Socket socket;
            //2.通过 accept()方法监听客户端请求， 这个方法会一直阻塞到有一个连接建立
            while ((socket = server.accept()) != null) {
                log.info("client connected");
                try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
                    // 反序列化成student对象
                    //3.通过输入流读取客户端发送的请求信息
                    Student student = (Student) objectInputStream.readObject();
                    log.info("server receive name:" + student.getName());
                    //4.通过输出流向客户端发送响应信息
                    student.setAge(20);
                    objectOutputStream.writeObject(student);
                    objectOutputStream.flush();
                } catch (IOException | ClassNotFoundException e) {
                    log.error("server accept error:", e);
                }
            }
        } catch (IOException e) {
            log.error("SocketHelloServer start error:", e);
        }
    }

    /**
     * socket多线程实现
     *
     * @param port
     */
    public void multiThreadStart(int port) {
        //1.创建 ServerSocket 对象并且绑定一个端口
        try (ServerSocket server = new ServerSocket(port)) {
            Socket socket = server.accept();
            //2.通过 accept()方法监听客户端请求， 这个方法会一直阻塞到有一个连接建立
            new Thread(() -> {
                // 创建 socket 连接
                log.info("client connected");
                try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
                    // 反序列化成student对象
                    //3.通过输入流读取客户端发送的请求信息
                    Student student = (Student) objectInputStream.readObject();
                    log.info("server receive name:" + student.getName());
                    //4.通过输出流向客户端发送响应信息
                    student.setAge(20);
                    objectOutputStream.writeObject(student);
                    objectOutputStream.flush();
                } catch (IOException | ClassNotFoundException e) {
                    log.error("server accept error:", e);
                }
            }).start();

        } catch (IOException e) {
            log.error("SocketHelloServer start error:", e);
        }
    }

    public static void main(String[] args) {
        SocketHelloServer helloServer = new SocketHelloServer();
        helloServer.start(6666);
    }
}
