package cn.joruachan.study.cs.nio;

import cn.joruachan.study.cs.TimeServerDefaultHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * Nio Time客户端
 *
 * @author JoruaChan
 */
public class NioTimeClient implements Runnable {

    private Selector selector;
    private int remotePort;

    public NioTimeClient(int remotePort, Selector selector) {
        this.remotePort = remotePort;
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            // 如果立即建立连接，则关注可读事件
            boolean connected = socketChannel.connect(new InetSocketAddress(remotePort));
            if (connected) {
                socketChannel.register(selector, SelectionKey.OP_READ, this);
            } else {
                socketChannel.register(selector, SelectionKey.OP_CONNECT, this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handle(SocketChannel socketChannel) {
        try {
            int count = (int) (Math.random() * 10);
            for (int i = 0; i < count; i++) {
                String sendMsg = "";
                if (i % 3 != 0) {
                    sendMsg = TimeServerDefaultHandler.QUESTION + ", ";
                }
                sendMsg += ("I am Client No." + i + "\n");

                byte[] bytes = sendMsg.getBytes(StandardCharsets.UTF_8);
                ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
                byteBuffer.put(bytes);

                byteBuffer.flip();
                socketChannel.write(byteBuffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
