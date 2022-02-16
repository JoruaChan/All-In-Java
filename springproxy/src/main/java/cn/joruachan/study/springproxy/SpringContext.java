package cn.joruachan.study.springproxy;

import cn.joruachan.study.ProxyUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 上下文<br>
 * 测试结果：
 * 1. 部分bean是自己类；部分bean是增强的代理类；
 * <p>
 * 何时生成代理的对象？
 *
 * @author JoruaChan
 */
public class SpringContext {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfig.class);

        printBeans(applicationContext);

        Landlord landlord = applicationContext.getBean(Landlord.class);
        landlord.sell();

        /********** 只要类实现了接口（Serializable不行），就会使用Jdk代理 ***********/
        // 代理类实际是Proxy子类，不能强转成自己的类
        // 错误：JdkProxyTestImpl iTest = (JdkProxyTestImpl) applicationContext.getBean("iProxy");
        IJdkProxyTest iTest = (IJdkProxyTest) applicationContext.getBean("iProxy");
        iTest.test();


        Object proxy = applicationContext.getBean("iProxy");
        System.out.println(ProxyUtil.targetFromSpringJdkProxy(proxy));
    }

    public static void printBeans(ApplicationContext ac) {
        for (String beanDefinitionName : ac.getBeanDefinitionNames()) {
            System.out.println("beanDefinitionName:" + beanDefinitionName);
            System.out.println("class:" + ac.getBean(beanDefinitionName).getClass());
            System.out.println("--------------------------------");
        }
    }

}
