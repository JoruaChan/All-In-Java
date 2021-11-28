package cn.joruachan.study.cs.fakeaio;

import cn.joruachan.study.cs.TimeDefaultClient;

import java.util.concurrent.CountDownLatch;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
public class TimeFakeAioMain {

    public static void main(String[] args) {
        // 开启Server
        final int remotePort = 8081;
        FakeAioTimeServer server = new FakeAioTimeServer();
        server.start(remotePort, 1,4, 100);

        int clientCount = 4;
        CountDownLatch countDownLatch = new CountDownLatch(clientCount);
        for (int i = 0; i < clientCount; i++) {
            new Thread(() -> {
                TimeDefaultClient.newInstance(remotePort).start();

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
