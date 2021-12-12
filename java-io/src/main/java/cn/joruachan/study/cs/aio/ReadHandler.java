package cn.joruachan.study.cs.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static cn.joruachan.study.cs.TimeServerDefaultHandler.QUESTION;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
public class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel socketChannel;

    public ReadHandler(AsynchronousSocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        // 切换成读状态
        attachment.flip();

        // 将数据读到数组中
        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);

        // 处理业务逻辑
        String line = new String(body, StandardCharsets.UTF_8);
        System.out.println("Server has received message: " + line);

        String sendMsg;
        if (line.startsWith(QUESTION)) {
            sendMsg = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } else {
            sendMsg = "Oh, what is your mean?";
        }

        byte[] sendBytes = sendMsg.getBytes(StandardCharsets.UTF_8);
        ByteBuffer writeByteBuffer = ByteBuffer.allocate(sendBytes.length);
        writeByteBuffer.put(sendBytes);
        // 切成读状态
        writeByteBuffer.flip();

        socketChannel.write(writeByteBuffer, writeByteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if (attachment.hasRemaining()) {
                    // 如果还没发送完，则继续写，类似链式调用
                    socketChannel.write(attachment, attachment, this);
                }
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

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        exc.printStackTrace();
        try {
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
