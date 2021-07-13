package cn.joruachan.study.newio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

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


    public void listen() throws IOException {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);

        socketChannel.bind(new InetSocketAddress(PORT));
    }

}
