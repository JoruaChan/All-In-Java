package cn.joruachan.study.cs.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Set;

import static cn.joruachan.study.cs.TimeServerDefaultHandler.QUESTION;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
public class NioTimeServer {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    public void start(int port) throws IOException {
        // 开启一个selector，用于事件轮询
        Selector selector = Selector.open();
        this.selector = selector;

        // 开启Server的Channel，并注册到Selector上
        ServerSocketChannel channel = ServerSocketChannel.open();
        this.serverSocketChannel = channel;

        channel.configureBlocking(false);
        channel.bind(new InetSocketAddress(port));

        channel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("-----------NIO Time Server Started!");
        // 通过Selector轮询事件
        recycleEvent();
    }

    public void stop() throws IOException {
        if (selector != null) {
            selector.close();
        }

        if (serverSocketChannel != null) {
            serverSocketChannel.close();
        }
    }

    private void recycleEvent() {
        while (selector.isOpen()) {
            int selectCount = 0;
            try {
                selectCount = selector.select(5000);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (selectCount > 0) {
                Set<SelectionKey> keySet = selector.selectedKeys();

                // 注意：keySet需要用迭代器来处理，因为key处理完需要remove掉
                // 不然处理完了，下次还会再次被处理
                Iterator<SelectionKey> iterator = keySet.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        // 连接请求, 【ServerSocketChannel的事件！！！】
                        ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.channel();

                        try {
                            // 先接收连接，再关注该通道的可读可写事件
                            SocketChannel clientChannel = serverChannel.accept();

                            // 这里同样要设置非阻塞！！！
                            clientChannel.configureBlocking(false);
                            clientChannel.register(selector, SelectionKey.OP_READ);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (selectionKey.isReadable()) {
                        // 可读事件
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        System.out.println("-----------Server 收到可读事件 -------------");

                        // 先分配ByteBuffer
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        try {
                            // 简单化了，最多只读取1024个字节
                            int length = socketChannel.read(byteBuffer);

                            if (length > 0) {
                                // 此时ByteBuffer处于被写入的状态，需要flip切换成读的状态
                                byteBuffer.flip();

                                byte[] bytes = new byte[byteBuffer.remaining()];
                                byteBuffer.get(bytes);

                                String receivedMsg = new String(bytes, StandardCharsets.UTF_8);
                                System.out.println("-----------Server 收到: " + receivedMsg + " -------------");

                                String sendMsg;
                                if (receivedMsg.startsWith(QUESTION)) {
                                    sendMsg = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                                } else {
                                    sendMsg = "Oh, what is your mean?";
                                }

                                byte[] sendBytes = sendMsg.getBytes(StandardCharsets.UTF_8);
                                ByteBuffer writeBuffer = ByteBuffer.allocate(sendBytes.length);
                                writeBuffer.put(sendBytes);

                                // !!!!写完记得要翻转，变成待读的状态
                                writeBuffer.flip();

                                socketChannel.write(writeBuffer);
                            } else {
                                // 直到没有数据了，关闭通道
                                socketChannel.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (selectionKey.isWritable()) {
                        // 可写事件
                    }

                    iterator.remove();
                }
            }

        }
    }
}
