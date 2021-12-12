package cn.joruachan.study.cs.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
public class TimeAioServer implements Runnable {

    private CountDownLatch countDownLatch;
    private AsynchronousServerSocketChannel serverSocketChannel;

    public TimeAioServer(int port) throws IOException {
        // 开启一个ServerSocket
        serverSocketChannel = AsynchronousServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));

        System.out.println("-------- AIO Time Server started!");
    }

    public AsynchronousServerSocketChannel getServerSocketChannel() {
        return serverSocketChannel;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    @Override
    public void run() {
        // 每次都要New一个，是因为防止accept失败后，能继续接收到其他的accept请求！！
        countDownLatch = new CountDownLatch(1);
        serverSocketChannel.accept(this, new AcceptHandler());
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
