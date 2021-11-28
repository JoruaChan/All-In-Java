package cn.joruachan.study.newio.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * 文件Channel<br>
 * 使用FileChannel拷贝文件
 *
 * @author JoruaChan
 * @see java.nio.channels.FileChannel
 */
public class NioCopyFile {
    public static final void copy(String fromFilePath, String toFilePath) throws IOException {
        File toFile = new File(toFilePath);
        if (!toFile.exists()) {
            toFile.createNewFile();
        }

        RandomAccessFile file = new RandomAccessFile(fromFilePath, "rw");
        FileChannel fileChannel = file.getChannel();
    }
}
