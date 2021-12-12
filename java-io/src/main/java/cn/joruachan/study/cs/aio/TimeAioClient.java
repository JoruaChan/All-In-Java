package cn.joruachan.study.cs.aio;

import cn.joruachan.study.cs.TimeServerDefaultHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

/**
 * Aio Client
 *
 * @author JoruaChan
 */
public class TimeAioClient implements CompletionHandler<Void, TimeAioClient> {
    private AsynchronousSocketChannel socketChannel;

    public void start(int port) {
        try {
            socketChannel = AsynchronousSocketChannel.open();
            socketChannel.connect(new InetSocketAddress(port), this, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void completed(Void result, TimeAioClient attachment) {
        String sendMsg = TimeServerDefaultHandler.QUESTION;

        byte[] sendBytes = sendMsg.getBytes(StandardCharsets.UTF_8);
        ByteBuffer byteBuffer = ByteBuffer.allocate(sendBytes.length);
        byteBuffer.put(sendBytes);
        byteBuffer.flip();

        socketChannel.write(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if (attachment.hasRemaining()) {
                    // 还有则继续写
                    socketChannel.write(attachment, attachment, this);
                } else {
                    // 写完了则读数据
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    socketChannel.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            attachment.flip();
                            byte[] bytes = new byte[attachment.remaining()];
                            attachment.get(bytes);

                            System.out.println(Thread.currentThread().getName() + " has received message:" +
                                    new String(bytes, StandardCharsets.UTF_8));
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            exc.printStackTrace();
                            try {
                                socketChannel.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });
    }

    @Override
    public void failed(Throwable exc, TimeAioClient attachment) {
        exc.printStackTrace();
        try {
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
