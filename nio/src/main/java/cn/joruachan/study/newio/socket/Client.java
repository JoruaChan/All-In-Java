package cn.joruachan.study.newio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * NIO下的客户端实践<br>
 *
 * @author JoruaChan
 */
public class Client {
    /**
     * 未连接
     */
    private static final int STATE_DISCONNECT = 0;

    /**
     * 连接中
     */
    private static final int STATE_CONNECTING = 1;

    /**
     * 已连接
     */
    private static final int STATE_CONNECTED = 2;

    // 默认状态未连接
    private int state = STATE_DISCONNECT;

    // 套接字Channel
    private SocketChannel socketChannel;

    public Client() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        // 设置成非阻塞连接
        socketChannel.configureBlocking(false);

        this.socketChannel = socketChannel;
    }

    public synchronized void connect() throws IOException {
        if (socketChannel.isConnected()) {
            return;
        }

        this.socketChannel.connect(new InetSocketAddress("127.0.0.1", Server.PORT));

        this.state = STATE_CONNECTING;
        while (!this.socketChannel.finishConnect()) {
            // 等待连接
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.state = STATE_CONNECTED;
    }

    public synchronized void disconnect() throws IOException {
        this.socketChannel.close();
        this.state = STATE_DISCONNECT;
    }

    public int getState() {
        return state;
    }
}
