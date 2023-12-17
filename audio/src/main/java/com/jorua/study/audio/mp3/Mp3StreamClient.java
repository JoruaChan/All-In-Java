package com.jorua.study.audio.mp3;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDeviceBase;
import javazoom.jl.player.Player;

import java.io.*;
import java.net.Socket;

/**
 * MP3客户端
 *
 * @author JoruaChan
 */
public class Mp3StreamClient {

    private Socket socket;

    public void start() {
        try {
            Socket socket = new Socket("localhost", 8888);
            this.socket = socket;

            InputStream inputStream = socket.getInputStream();

            Player player = new Player(inputStream);
            player.play();

            // 播完关闭
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, JavaLayerException {

//        Bitstream bitstream = new Bitstream(fileInputStream);
//        Decoder decoder = new Decoder();
//
//        Header h = bitstream.readFrame();
//        SampleBuffer output = (SampleBuffer)decoder.decodeFrame(h, bitstream);
//
//            out.write(output.getBuffer(), 0, output.getBufferLength());

//        File file = new File("/Users/jorua/Downloads/同花顺/test.mp3");
//        file.delete();
//        file.createNewFile();
//
//        FileInputStream fileInputStream1 = new FileInputStream(file);
//        Player player = new Player(fileInputStream1, new DemoAudioDevice(new File("/Users/jorua/Downloads/同花顺/mp3ToPcm.pcm")));
//        player.play();

        Mp3Server mp3Server = new Mp3Server();
        mp3Server.start();
        // 等待server准备好了
        mp3Server.waitServerOk();

        // 读mp3
        new Mp3StreamClient().start();
    }

    public static final class DemoAudioDevice extends AudioDeviceBase {

        private byte[] byteBuf = new byte[4096];
        private FileOutputStream fileOutputStream;

        public DemoAudioDevice(File savedFile) {
            savedFile.delete();
            try {
                savedFile.createNewFile();
                fileOutputStream = new FileOutputStream(savedFile, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void openImpl() throws JavaLayerException {
            super.openImpl();
            System.out.println("开始播放！！！！");
        }

        @Override
        protected void closeImpl() {
            super.closeImpl();
            System.out.println("播放结束, 获取到采样率:" + getDecoder().getOutputFrequency());

            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void writeImpl(short[] samples, int offs, int len) throws JavaLayerException {
            super.writeImpl(samples, offs, len);
            System.out.println("获取到数据");

            byte[] byteArray = toByteArray(samples, offs, len);
            try {
                fileOutputStream.write(byteArray, 0, len * 2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        protected byte[] getByteArray(int length) {
            if (byteBuf.length < length) {
                byteBuf = new byte[length + 1024];
            }
            return byteBuf;
        }

        protected byte[] toByteArray(short[] samples, int offs, int len) {
            byte[] b = getByteArray(len * 2);
            int idx = 0;
            short s;
            while (len-- > 0) {
                s = samples[offs++];
                b[idx++] = (byte) s;
                b[idx++] = (byte) (s >>> 8);
            }
            return b;
        }

        @Override
        protected void flushImpl() {
            super.flushImpl();
            System.out.println("最后一帧 主动flush");
        }

        @Override
        public int getPosition() {
            System.out.println("获取当前播放位置");
            return 0;
        }
    }

}
