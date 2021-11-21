package cn.joruachan.study.cs.bio;

import cn.joruachan.study.cs.TimeServerDefaultHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO Server<br>
 * 为了方便，读一行，返回结果
 *
 * @author JoruaChan
 */
public class TimeBioServer {

    private ServerSocket serverSocket;

    private boolean closed = false;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("当前线程：" + Thread.currentThread().getName() + "，运行Server!");

            new Thread(() -> new Acceptor().accept()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        this.closed = true;
    }

    private class Acceptor {
        public void accept() {
            while (!closed) {
                try {
                    Socket socket = serverSocket.accept();

                    if (socket != null) {
                        new Thread(new TimeServerDefaultHandler(socket), "ServerHandler").start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Server 关闭了啊！！！");
        }
    }
}
