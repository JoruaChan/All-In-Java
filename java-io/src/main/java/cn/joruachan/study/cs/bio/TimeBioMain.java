package cn.joruachan.study.cs.bio;

import cn.joruachan.study.cs.TimeDefaultClient;

import java.util.concurrent.CountDownLatch;

/**
 * 阻塞IO模式 C/S <br>
 * <p>
 * 请求数:线程数=1:1，资源有限
 * <p>
 * 一般性情况下, 要注意：
 * <ul>
 *     <li> Client发送的数据量并不能确定；</li>
 *     <li> 只要Client不close流, 利用byte[]数组缓冲从InputStream中读取, Server会阻塞直到读到数据；</li>
 *     <li> 不能在没有读到结果前关闭流，这样会导致Socket关闭；所以需要在需要在协议上定义出结束的标记；</li>
 * </ul>
 * <strong>对于本例, Client处理完就要把流关闭，不然Server会在read地方一直阻塞；</strong>
 *
 * @author JoruaChan
 */
public class TimeBioMain {
    public static void main(String[] args) {
        TimeBioServer server = new TimeBioServer();
        server.start(8080);

        int clientCount = 4;
        CountDownLatch countDownLatch = new CountDownLatch(clientCount);
        for (int i = 0; i < clientCount; i++) {
            new Thread(() -> {
                TimeDefaultClient.newInstance(8080).start();

                countDownLatch.countDown();
            }, "thread-" + (i + 1))
                    .start();
        }

        try {
            countDownLatch.await();
            server.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
