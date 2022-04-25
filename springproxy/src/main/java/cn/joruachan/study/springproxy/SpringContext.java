package cn.joruachan.study.springproxy;

import cn.joruachan.study.ProxyUtil;
import cn.joruachan.study.springproxy.introduction.MixInAdvisor;
import cn.joruachan.study.springproxy.introduction.MixInLock;
import cn.joruachan.study.springproxy.jdk.BeDepend;
import cn.joruachan.study.springproxy.jdk.BeProxied;
import cn.joruachan.study.springproxy.jdk.EmptyBeProxied;
import cn.joruachan.study.springproxy.jdk.IProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
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

    private final static Logger LOGGER = LoggerFactory.getLogger(SpringContext.class);

    public static void main(String[] args) {
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        final String basePackage = "cn.joruachan.study.springproxy";
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(basePackage);


//        String[] factoryPostProcessors = applicationContext.getBeanNamesForType(BeanFactoryPostProcessor.class);
//        LOGGER.info("BeanFactoryPostProcessor:{}",factoryPostProcessors);

//        printBeans(applicationContext);

//        // IntroductionAdvisor
//        MixInAdvisor mixInAdvisor = (MixInAdvisor) applicationContext.getBean("mixInAdvisor");
//        // IntroductionInterceptor
//        MixInLock mixInLock = (MixInLock) mixInAdvisor.getAdvice();
//        // 上锁
//        mixInLock.lock();

//        try {
//            // 没有方法的接口被代理
//            EmptyBeProxied emptyBeProxied =
//                    (EmptyBeProxied) applicationContext.getBean("emptyBeProxied");
//            LOGGER.info("空代理:{}", emptyBeProxied);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // 再解锁
//        mixInLock.unlock();

        // 通过ProxyFactoryBean获取代理对象，方法执行会被拦截
//        Object proxyFactoryBean1 = applicationContext.getBean("codingProxyFactory");
//        ((IProxy)proxyFactoryBean1).execute();

        // 注意：直接从BeanFactory中获取Target，方法执行不会拦截
//        IProxy target = (IProxy) applicationContext.getBean("codingTarget");
//        target.execute();

//        // 正常Jdk代理，代理接口方法
//        // 非接口方法，代理不了！
//        Object beProxied = applicationContext.getBean("beProxied");
//        ((IProxy) beProxied).execute();
//
//        // 获取被代理的对象!
//        System.out.println(ProxyUtil.targetFromSpringJdkProxy(beProxied));
//
//        // 由于被代理的类在IOC容器中是Proxy类型，所以不能直接使用实现类Autowired注入到其他类中；
//        BeDepend beDepend = (BeDepend) applicationContext.getBean("beDepend");
//        beDepend.depend();
//
//        // 如果同一个类中调用被拦截的方法，也是拦截不到的
//        IProxy innerCall = (IProxy) applicationContext.getBean("innerCall");
//        innerCall.execute();
//
//        // 如果只拦截部分接口方法，对生成代理类无影响。
//        // 只不过在InvocationHandle调用时，会判断需不需要执行Advice织入的代码；
    }

    public static void printBeans(ApplicationContext ac) {
        LOGGER.info("\n\n\n------------------ PrintBeans Start ------------------------\n\n\n");
        for (String beanDefinitionName : ac.getBeanDefinitionNames()) {
            LOGGER.info("bean:{}, actual class:{}\n", beanDefinitionName, ac.getBean(beanDefinitionName).getClass());
        }
        LOGGER.info("\n\n\n------------------ PrintBeans End ------------------------\n\n\n");
    }

}
