package cn.joruachan.study.cs.fakeaio;

import cn.joruachan.study.cs.TimeServerDefaultHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 伪异步IO Server<br>
 * 将请求分配到线程池来处理
 *
 * @author JoruaChan
 */
public class FakeAioTimeServer {

    private ServerSocket serverSocket;
    private Executor executor;

    private boolean closed = false;

    /**
     * 开启Server
     *
     * @param port        端口
     * @param core        核心线程数，恒在的线程
     * @param max         最多存在的线程数
     * @param queueLength 等待获取线程的队列大小
     */
    public void start(int port, int core, int max, int queueLength) {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(6000);

            executor = new ThreadPoolExecutor(core, max,
                    60L, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(queueLength));

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
            System.out.println("Server线程准备accept请求Socket");
            while (!closed) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("收到Socket请求！");
                    if (socket != null) {
                        executor.execute(new TimeServerDefaultHandler(socket));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Server 关闭了啊！！！");
        }
    }
}
