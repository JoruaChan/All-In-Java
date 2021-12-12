package cn.joruachan.study.cs.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * AIO服务器Accept的异步处理<br>
 * <strong>注意Accept在完成completed后, 要手动再accept</strong>
 *
 * @author JoruaChan
 */
public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, TimeAioServer> {

    @Override
    public void completed(AsynchronousSocketChannel result, TimeAioServer attachment) {
        // 这里要注意，ServerSocketChannel虽然是线程安全的, 但是同一时间只能有一处accept；
        // 所以，当一个Accept被处理完成，需要及时调用accept方法继续下一个accept连接
        attachment.getServerSocketChannel().accept(attachment, this);

        // 连接建立完成，就开始读数据
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        result.read(byteBuffer, byteBuffer, new ReadHandler(result));
    }

    @Override
    public void failed(Throwable exc, TimeAioServer attachment) {
        exc.printStackTrace();
        attachment.getCountDownLatch().countDown();
    }
}
