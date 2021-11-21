package cn.joruachan.study.cs;

import java.io.*;
import java.net.Socket;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * 默认的处理器<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
public class TimeServerDefaultHandler implements Runnable {
    public static final String QUESTION = "WHAT TIME IS IT?";

    private Socket socket;

    public TimeServerDefaultHandler(Socket socket) {
        this.socket = socket;
    }

    private void handle() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = this.socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            outputStream = this.socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);

            String line = reader.readLine();
            System.out.println("Server has received message: " + line);

            String sendMsg;
            if (line.startsWith(QUESTION)) {
                sendMsg = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } else {
                sendMsg = "Oh, what is your mean?";
            }
            writer.println(sendMsg);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void run() {
        handle();
    }
}
