package cn.joruachan.study.copy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 序列化深拷贝<br>
 *
 * @author JoruaChan
 */
public class DeepCopySerialObject implements Serializable {

    public static void main(String[] args) throws IOException {
        DeepCopySerialObject object = new DeepCopySerialObject();

        FileOutputStream fos = new FileOutputStream("filename");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(object);
    }
}
