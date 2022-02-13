package cn.joruachan.study.jdkproxy;

/**
 * 做些事情的接口
 *
 * @author JoruaChan
 */
public interface IDoSomething {

    void doSomething();

    default void beforeDo(String whatToSay, int age) {
        System.out.println("Before Do Say:" + whatToSay + ",age:" + age);
    }
}
