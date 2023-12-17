package cn.jorua.study;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
public class NotInitialization {
    public static void main(String[] args) {
        // 不会初始化子类，而是初始化父类
        // 这里的value不能被final修饰，否则直接加入到常量池，无需初始化此类
        // System.out.println(SubClass.value);

        // newarray，创建的是虚拟机生成的继承Object的数组类对象
        // SuperClass[] superClasses = new SuperClass[10];

        // 不会初始化ConstantClass类，编译期存在此类NotInitialization的常量池了
        // System.out.println(ConstantClass.value);

    }
}
