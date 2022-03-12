package cn.joruachan.study.springproxy;

import cn.joruachan.study.ProxyUtil;
import cn.joruachan.study.springproxy.jdk.BeDepend;
import cn.joruachan.study.springproxy.jdk.EmptyBeProxied;
import cn.joruachan.study.springproxy.jdk.IProxy;
import cn.joruachan.study.springproxy.jdk.IProxy1;
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
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        final String basePackage = "cn.joruachan.study.springproxy";
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(basePackage);

        printBeans(applicationContext);

        // 没有方法的接口被代理
        EmptyBeProxied emptyBeProxied =
                (EmptyBeProxied) applicationContext.getBean("emptyBeProxied");
        System.out.println(emptyBeProxied);

        // 正常Jdk代理，代理接口方法
        // 非接口方法，代理不了！
        Object beProxied = applicationContext.getBean("beProxied");
        ((IProxy) beProxied).execute();

        // 获取被代理的对象!
        System.out.println(ProxyUtil.targetFromSpringJdkProxy(beProxied));

        // 由于被代理的类在IOC容器中是Proxy类型，所以不能直接使用实现类Autowired注入到其他类中；
        BeDepend beDepend = (BeDepend) applicationContext.getBean("beDepend");
        beDepend.depend();

        // 如果同一个类中调用被拦截的方法，也是拦截不到的
        IProxy innerCall = (IProxy) applicationContext.getBean("innerCall");
        innerCall.execute();

        // 如果只拦截部分接口方法，对生成代理类无影响。
        // 只不过在InvocationHandle调用时，会判断需不需要执行Advice织入的代码；
    }

    public static void printBeans(ApplicationContext ac) {
        for (String beanDefinitionName : ac.getBeanDefinitionNames()) {
            System.out.println("beanDefinitionName:" + beanDefinitionName);
            System.out.println("class:" + ac.getBean(beanDefinitionName).getClass());
            System.out.println("--------------------------------");
        }
    }

}
