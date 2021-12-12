package cn.joruachan.study.cs.aio;

import java.io.IOException;

/**
 * AIO主函数
 *
 * @author JoruaChan
 */
public class TimeAioMain {

    public static void main(String[] args) throws IOException {
        TimeAioServer server = new TimeAioServer(8080);
        new Thread(server, "Aio-Server").start();

        for (int i = 0; i < 10; i++) {
            TimeAioClient client = new TimeAioClient();
            new Thread(() -> {
                client.start(8080);
            }, "Aio-Client-" + (i + 1)).start();
        }
    }

}
