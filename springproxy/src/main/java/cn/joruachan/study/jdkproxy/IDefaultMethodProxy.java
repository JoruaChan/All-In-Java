package cn.joruachan.study.jdkproxy;

/**
 * 接口存在默认方法，看下代理类会怎么处理
 *
 * @author JoruaChan
 */
public interface IDefaultMethodProxy {
    default String defaultMethod(String name) {
        return "defaultMethod:" + name;
    }
}
