package cn.joruachan.study.newio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO下的服务端实践<br>
 *
 * @author JoruaChan
 */
public class Server {
    /**
     * 服务器端暴露的端口
     */
    public static final int PORT = 8090;

    private Selector selector;
    private ServerSocketChannel socketChannel;

    public Server() throws IOException {
        this.selector = Selector.open();
    }

    public void listen() throws IOException {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.bind(new InetSocketAddress(PORT));
        System.out.println("服务器启动成功！");

        this.socketChannel = socketChannel;

        socketChannel.register(this.selector, SelectionKey.OP_ACCEPT);

        new Thread(new Bootstrap()).start();
    }

    public class Bootstrap implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    if (selector.select() > 0) {
                        Set<SelectionKey> keys = selector.selectedKeys();
                        Iterator<SelectionKey> iterator = keys.iterator();
                        while (iterator.hasNext()) {
                            SelectionKey key = iterator.next();
                            if (key.isAcceptable()) {
                                accept(key);
                            } else if (key.isReadable()) {
                                read(key);
                            }

                            iterator.remove();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void accept(SelectionKey acceptKey) throws IOException {
            System.out.println("新的accept请求!");
            SocketChannel acceptChannel = socketChannel.accept();
            acceptChannel.configureBlocking(false);
            acceptChannel.register(selector, SelectionKey.OP_READ);
        }

        private void read(SelectionKey readKey) throws IOException {
            System.out.println("新的read请求!");
            SocketChannel channel = (SocketChannel) readKey.channel();
            channel.configureBlocking(false);

            try {
                ByteBuffer buffer = ByteBuffer.allocate(1024);

                int length;
                while ((length = channel.read(buffer)) > 0) {
                    // channel.read时，buffer处于写状态，现在要读，就要flip
                    buffer.flip();
                    System.out.println("读到数据:" + new String(buffer.array(), 0, length));
                    // 读完了，clear,转成写状态
                    buffer.clear();
                }
            } finally {
                // 读完了，关闭通道
                channel.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Server().listen();
    }
}
