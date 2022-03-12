package cn.joruachan.study.springproxy.jdk;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Component;

/**
 * 内部调用拦截方法
 *
 * @author JoruaChan
 */
@Component
public class InnerCall implements IProxy, IProxy1 {

    @Override
    public void execute() {
        System.out.println("Before InnerCall#execute2.");

        // 直接调用execute2是不会被拦截的
        // 必须通过AopContext.currentProxy来调用
        // 要打开exposeProxy=true的开关
        ((IProxy1) AopContext.currentProxy()).execute2();

        System.out.println("After InnerCall#execute2.");
    }

    @Override
    public void execute2() {
        System.out.println("Executing InnerCall#execute2.");
    }
}
