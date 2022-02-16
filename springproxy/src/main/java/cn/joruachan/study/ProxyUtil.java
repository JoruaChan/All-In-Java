package cn.joruachan.study;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * 代理工具类
 *
 * @author JoruaChan
 */
public class ProxyUtil {

    /**
     * 从JDK代理获得被代理的对象
     * <p>
     * 1. JdkDynamicAopProxy实现了InvocationHandler接口；
     * 2. 代理类(Proxy子类)中的h参数就是JdkDynamicAopProxy；
     * 3. Spring中通过AdvisedSupport.targetSource保存了被代理的对象包装类TargetSource；
     * 4. TargetSource作为被代理对象的包装类，可通过getTarget获得被代理对象
     *
     * @param proxyObj
     * @return
     */
    public static final Object targetFromSpringJdkProxy(Object proxyObj) {
        if (proxyObj == null) {
            throw new IllegalArgumentException("代理对象不可为空!");
        }

        Class superClass = proxyObj.getClass().getSuperclass();
        if (!Proxy.class.equals(superClass)) {
            throw new IllegalArgumentException("非Jdk代理对象!");
        }

        try {
            Field field = superClass.getDeclaredField("h");

            // TODO: 添加安全检查
            // SecurityManager securityManager = System.getSecurityManager();
            field.setAccessible(true);
            Object invocationHandler = field.get(proxyObj);
            if (invocationHandler == null) {
                throw new IllegalArgumentException("Jdk代理对象无InvocationHandler!");
            }


            if (!(invocationHandler instanceof AopProxy)) {
                throw new IllegalArgumentException("Jdk代理对象无InvocationHandler!");
            }

            // JdkDynamicAopProxy对象，但是不可访问
            AopProxy aopProxy = (AopProxy) invocationHandler;

            // 注意: 这个要用aopProxy.getClass获得不可访问的JdkDynamicAopProxy才行
            Field advised = aopProxy.getClass().getDeclaredField("advised");
            advised.setAccessible(true);
            // 获得AdvisedSupport
            AdvisedSupport advisedSupport = (AdvisedSupport) advised.get(aopProxy);
            return advisedSupport.getTargetSource().getTarget();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new Error("没找到方法h参数");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new Error("h参数不可访问!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e.getCause());
        }
    }

}
