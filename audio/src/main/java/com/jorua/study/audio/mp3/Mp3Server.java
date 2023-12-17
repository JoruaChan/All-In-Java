package com.jorua.study.audio.mp3;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * MP3服务器
 * <p>
 * 从文件中读取MP3内容，再通过Socket流返回给客户端
 *
 * @author JoruaChan
 */
public class Mp3Server {

    ServerSocket serverSocket;

    private boolean ok = false;

    public void start() {
        new Thread(() -> start0()).start();
    }

    private void start0() {
        try {
            this.serverSocket = new ServerSocket(8888);
            System.out.println("等待连接....");

            ok = true;

            Socket socket = serverSocket.accept();
            System.out.println("客户端已连接");

            OutputStream outputStream = socket.getOutputStream();

            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream("/Users/jorua/Downloads/同花顺/openai-test.mp3");

                byte[] bytes = new byte[2048];
                int len;
                while ((len = fileInputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, len);
                    Thread.sleep(500);
                }
                outputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("文件内容传输完毕");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitServerOk() {
        while (!ok) {
            System.out.println("Server还没好");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Server好了");
    }

    public void stop() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
