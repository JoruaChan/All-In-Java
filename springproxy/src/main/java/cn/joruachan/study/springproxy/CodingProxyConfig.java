package cn.joruachan.study.springproxy;

import cn.joruachan.study.springproxy.jdk.BeProxied;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * 编码代理
 *
 * @author JoruaChan
 */
@Configuration
public class CodingProxyConfig {

    @Bean
    public BeProxied codingTarget() {
        return new BeProxied();
    }

    @Bean
    public MethodBeforeAdvice codingBeforeAdvice() {
        return new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("before method:" + method.getName() + ", target:" + target);
            }
        };
    }

    @Bean
    public ProxyFactoryBean codingProxyFactory(MethodBeforeAdvice codingBeforeAdvice) throws ClassNotFoundException {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();

        // 指定方法拦截的Advice、Interceptors
        proxyFactoryBean.setInterceptorNames("codingBeforeAdvice");

        // 指定代理对象
        proxyFactoryBean.setTargetName("codingTarget");

        // 指定代理的接口
        proxyFactoryBean.setProxyInterfaces(BeProxied.class.getInterfaces());

        // 是否单例
        proxyFactoryBean.setSingleton(false);

        return proxyFactoryBean;
    }

}
