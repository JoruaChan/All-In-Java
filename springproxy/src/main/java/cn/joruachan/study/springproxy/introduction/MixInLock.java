package cn.joruachan.study.springproxy.introduction;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;
import org.springframework.stereotype.Component;

/**
 * 利用Spring Aop的引入概念做的一个锁
 * <p>
 * DelegatingIntroductionInterceptor默认使用当前类的接口作为被引入的接口，及其实现对象；
 * 当处理引入接口时，就调用当前类进行处理；
 * <p>
 * 其他方法交给 {@link MethodInvocation#proceed()} 继续处理；
 *
 * @author JoruaChan
 */
public class MixInLock extends DelegatingIntroductionInterceptor implements Lockable {

    /**
     * 默认未上锁
     */
    private boolean lockState = false;

    @Override
    public void lock() {
        lockState = true;
    }

    @Override
    public void unlock() {
        lockState = false;
    }

    @Override
    public boolean isLocked() {
        return lockState;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        if (isLocked() && mi.getMethod().getName().startsWith("set")) {
            // 当上、了锁，且调用了set方法，就报错
            throw new IllegalStateException("已上锁!");
        }
        return super.invoke(mi);
    }
}
