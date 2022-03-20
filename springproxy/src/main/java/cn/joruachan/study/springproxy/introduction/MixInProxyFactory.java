package cn.joruachan.study.springproxy.introduction;

import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.BeansException;

/**
 * 自定义代理工厂
 *
 * @author JoruaChan
 */
public class MixInProxyFactory extends ProxyFactoryBean {

    public MixInProxyFactory() {
        setInterceptorNames();
    }

    @Override
    protected Object getProxy(AopProxy aopProxy) {
        return super.getProxy(aopProxy);
    }

    @Override
    public Object getObject() throws BeansException {
        return super.getObject();
    }
}
