package cn.joruachan.study.netty.ws.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;

/**
 * WebSocket客户端
 *
 * @author JoruaChan
 */
public class WebSocketClient implements Closeable {

    /**
     * 请求URL
     */
    private URI uri;

    private Bootstrap bootstrap;

    private Channel channel;

    public void start() {
        HttpHeaders headers = new DefaultHttpHeaders();
        headers.add("head1", "value1");

        WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory
                .newHandshaker(uri, WebSocketVersion.V13,
                        null, true, headers);

        WebSocketClientHandler handler = new WebSocketClientHandler(handshaker);

        // 创建Bootstrap，在调用链的最后一个环节，当ChannelActive时进行握手
        Bootstrap bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpClientCodec());
                        pipeline.addLast(new HttpObjectAggregator(8192));
                        pipeline.addLast(WebSocketClientCompressionHandler.INSTANCE);
                        pipeline.addLast(handler);
                    }
                });
        this.bootstrap = bootstrap;

        try {
            // 建立连接
            this.channel = bootstrap.connect(uri.getHost(), uri.getPort()).sync().channel();

            // 等待握手响应
            handler.handshakeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        if (this.channel != null) {
            this.channel.close();
        }
    }
}
