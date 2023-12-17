package cn.jorua.study.json;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
public class JavaPOJO {
    private int field1;
    private boolean field2;
    private String field3;
    private Object1 field4;

    static class Object1 {
        private int field1;
        private String field2;
        private Object2 field3;
    }

    static class Object2 {
        private String field2;
    }
}
