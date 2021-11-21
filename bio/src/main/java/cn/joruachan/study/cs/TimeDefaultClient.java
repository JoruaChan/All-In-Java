package cn.joruachan.study.cs;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * BIO Client<br>
 * 为了便捷，发一行请求，读一行结果
 *
 * @author JoruaChan
 */
public class TimeDefaultClient {

    public static TimeDefaultClient newInstance(int remotePort) {
        return new TimeDefaultClient(remotePort);
    }

    private int remotePort;

    public TimeDefaultClient(int remotePort) {
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
                    sendMsg = TimeServerDefaultHandler.QUESTION + ", ";
                }
                sendMsg += ("I am Client No." + i + ", " + Thread.currentThread().getName());
                writer.println(sendMsg);
            }

            String line = reader.readLine();
            System.out.println("Client has received message: " + line);
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
