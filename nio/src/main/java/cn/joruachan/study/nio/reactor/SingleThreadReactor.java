package cn.joruachan.study.nio.reactor;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Set;

/**
 * 单线程的Reactor模式<br>
 * 组成：reactor/acceptor/handler
 *
 * @author JoruaChan
 */
public class SingleThreadReactor implements Runnable {

    private Selector selector;

    public SingleThreadReactor(Selector selector, ServerSocketChannel socketChannel) throws ClosedChannelException {
        SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        selectionKey.attach(new Acceptor(selector, socketChannel));

        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // 一直循环查看事件，直到发生中断
                selector.select();

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                for (SelectionKey selectionKey : selectedKeys) {
                    dispatch(selectionKey);
                }
                selectedKeys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey selectionKey) {
        Runnable runnable = (Runnable) selectionKey.attachment();
        runnable.run();
    }

    class Acceptor implements Runnable {
        private Selector selector;
        private ServerSocketChannel serverSocketChannel;

        public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel) {
            this.selector = selector;
            this.serverSocketChannel = serverSocketChannel;
        }

        @Override
        public void run() {
            try {
                // 建立连接
                SocketChannel socketChannel = serverSocketChannel.accept();
                SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
                selectionKey.attach(new Handler());

                selector.wakeup();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Handler implements Runnable {

        @Override
        public void run() {

        }
    }
}