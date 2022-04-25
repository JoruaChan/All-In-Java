package cn.joruachan.study.springproxy.introduction;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

/**
 * 利用Spring Aop的引入概念做的一个锁
 * <p>
 * 通过DelegatingIntroductionInterceptor完成对MixInLock的代理引入
 * <p>
 * DelegatingIntroductionInterceptor,主要完成：
 * 1. 默认使用当前类对象作为delegate代理对象，也可以指定代理对象；（一级级遍历父类实现的接口都会作为引入接口）
 * 2. 使用delegate对象实现的接口作为被引入的接口；
 * 3. {@link #invoke} 当方法被调用时，会看Method是否属于引入的接口，如果则交给delegate对象去执行；否则交给{@link MethodInvocation#proceed()}执行；
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
        if (isLocked()){
                // && mi.getMethod().getName().startsWith("set")) {
            // 当上了锁，且调用了set方法，就报错
            throw new IllegalStateException("已上锁!");
        }
        return super.invoke(mi);
    }
}
