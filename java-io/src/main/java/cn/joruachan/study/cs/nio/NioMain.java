package cn.joruachan.study.cs.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * Nio C/S主函数<br>
 * <p>
 * 服务器：
 * Selector，用于轮询注册其中的事件；
 * Channel，数据双向传输的通道；
 *
 * @author JoruaChan
 */
public class NioMain {

    public static void main(String[] args) throws IOException {
        NioTimeServer nioTimeServer = new NioTimeServer();
        new Thread(()-> {
            try {
                nioTimeServer.start(8081);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        Selector clientSelector = Selector.open();
        for (int i = 0; i < 4; i++) {
            new Thread(new NioTimeClient(8081, clientSelector)).start();
        }

        CountDownLatch countDownLatch = new CountDownLatch(1);
        // 线程专门监听Client事件
        new Thread(() -> {
            while (clientSelector.isOpen()) {
                int selectCount = 0;
                try {
                    selectCount = clientSelector.select(5000);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (selectCount > 0) {
                    Set<SelectionKey> selectionKeys = clientSelector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

                    while (keyIterator.hasNext()) {
                        SelectionKey selectionKey = keyIterator.next();

                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        NioTimeClient nioTimeClient = (NioTimeClient) selectionKey.attachment();

                        if (selectionKey.isConnectable()) {
                            // 可以建立连接，则完成连接建立
                            try {
                                if (socketChannel.finishConnect()) {
                                    socketChannel.register(clientSelector, SelectionKey.OP_READ, nioTimeClient);

                                    // 连接建立了，则准备写入
                                    nioTimeClient.handle(socketChannel);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        if (selectionKey.isReadable()) {
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                            int length;
                            try {
                                while ((length = socketChannel.read(byteBuffer)) > 0) {
                                    // 准备读前，要将Buffer翻转
                                    byteBuffer.flip();

                                    byte[] bytes = new byte[length];
                                    byteBuffer.get(bytes);

                                    System.out.println("Server收到: " + new String(bytes, StandardCharsets.UTF_8));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        keyIterator.remove();
                    }
                }
            }
        }).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        nioTimeServer.stop();
    }
}
