package cn.joruachan.study.cs.bio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Se
 *
 * @author JoruaChan
 */
public class TimeBioServer {

    private ServerSocket serverSocket;

    private boolean closed = false;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("当前线程："+Thread.currentThread().getName()+"，运行Server!");
            new Acceptor().accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        this.closed = true;
    }

    private class Acceptor {
        public void accept() {
            while (!closed) {
                try {
                    Socket socket = serverSocket.accept();

                    if (socket != null) {
                        new Thread(new Handler(socket), "ServerHandler").start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Server 关闭了啊！！！");
        }
    }

    public static final String QUESTION = "WHAT TIME IS IT?";

    private class Handler implements Runnable {
        Socket socket;

        public Handler(Socket socket) {
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
                while (true) {
                    String line =  reader.readLine();
                    if (line == null) break;

                    System.out.println("Server has received message: " + line);

                    String sendMsg;
                    if (line.startsWith(QUESTION)) {
                        sendMsg = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    } else {
                        sendMsg = "Oh, what is your mean?";
                    }
                    writer.println(sendMsg);
                }
            } catch (IOException e) {
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
}
