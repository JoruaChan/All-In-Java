package cn.jorua.study;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
public class SuperClass {

    static {
        System.out.println("Super class init!");
    }

    // 这里的value不能被final修饰，否则直接加入到常量池，无需初始化此类
    public static int value = 123;
}
