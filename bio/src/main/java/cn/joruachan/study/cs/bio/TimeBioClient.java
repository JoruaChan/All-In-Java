package cn.joruachan.study.cs.bio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * BIO Client<br>
 * 为了便捷，发一行请求，读一行结果
 *
 * @author JoruaChan
 */
public class TimeBioClient {

    public static TimeBioClient newInstance(int remotePort) {
        return new TimeBioClient(remotePort);
    }

    private int remotePort;

    public TimeBioClient(int remotePort) {
        this.remotePort = remotePort;
    }

    public void start() {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(remotePort));

            inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);

            int count = (int) (Math.random() * 10);
            for (int i = 0; i < count; i++) {
                String sendMsg = "";
                if (i % 3 != 0) {
                    sendMsg = TimeBioServer.QUESTION + ", ";
                }
                sendMsg += ("I am Client No." + i + ", " + Thread.currentThread().getName());
                writer.println(sendMsg);
            }

            while (true) {
                String line = reader.readLine();
                if (line == null) break;

                System.out.println("Client has received message: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
