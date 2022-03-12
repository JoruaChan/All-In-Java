package cn.joruachan.study.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Jdk代理主函数
 *
 * @author JoruaChan
 */
public class JdkProxyMain {

    /**
     * 强调下：这里只是演示。
     * 实际应用中，生成代理对象前，是要有确定的被代理对象，并使用被代理对象实现的接口进行代理。
     * <p>
     * JDK提供的InvocationHandle接口实现的时候，是要能找到对应的被代理对象，才能在最后使用invoke进行代理。
     *
     * @param args
     */
    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        Object object;

        // 接口的默认方法
        object = defaultMethod();
        System.out.println(((IDefaultMethodProxy) object).defaultMethod("yeah"));

        // 相同方法签名，不同返回值
//        object = sameMethodDifferentReturn();
//        System.out.println(object);

        // 非公有接口
//        object = nonPublicInterface();
//        System.out.println(object);

        // 相同接口，不同顺序
//        Object[] objects = differentOrderInterfaces();
//        System.out.println(objects[0]);
//        System.out.println(objects[1]);

        // 两个重复的接口
//        object = sameInterfaces();
//        System.out.println(object);

        // 空方法的接口
//        object = emptyMethod();
//        System.out.println(object);
    }

    /**
     * 接口默认方法代理
     * <p>
     * 如果被代理对象重写了默认方法，代理类又想要调用其默认接口的方法，
     * <strong>就需要通过句柄的方式来处理；</strong>
     *
     * @return
     */
    public static final Object defaultMethod() {
        Object target = new IDefaultMethodProxy() {
            @Override
            public String defaultMethod(String name) {
                return "what?";
            }
        };
        return Proxy.newProxyInstance(JdkProxyMain.class.getClassLoader(),
                new Class<?>[]{IDefaultMethodProxy.class},
                new DoInvocation(target));
    }

    /**
     * 相同方法签名，不同返回值
     * <p>
     * 如果返回值类型一个是基础类型，另一个是包装类，会报错；
     * <p>
     * 如果返回值类型是没有父子类关系，会报错；
     * <p>
     * 如果返回值类型有父子类关系，则会生成两个代理方法，不是合并，就需要在
     * {@linkplain InvocationHandler#invoke(Object, Method, Object[])}中特殊处理；
     *
     * @return
     */
    private static final Object sameMethodDifferentReturn() {
        return Proxy.newProxyInstance(JdkProxyMain.class.getClassLoader(),
                new Class<?>[]{IProxy.class, IProxy2.class},
                new DoInvocation());
    }

    /**
     * 非公有接口
     * <p>
     * 生成的代理类会被放到私有的接口类所在包下
     *
     * @return
     */
    public static final Object nonPublicInterface() {
        return Proxy.newProxyInstance(JdkProxyMain.class.getClassLoader(),
                new Class<?>[]{IPrivateProxy.class},
                new DoInvocation());
    }

    /**
     * 相同接口，不同顺序
     * <p>
     * 会生成不同的代理类；
     * 因为WeakCache类中，二级缓存的key对象Key1、Key2、keyX对接口顺序敏感
     *
     * @return
     */
    public static final Object[] differentOrderInterfaces() {
        Object[] objects = new Object[2];
        objects[0] = Proxy.newProxyInstance(JdkProxyMain.class.getClassLoader(),
                new Class<?>[]{IProxy.class, IProxy2.class},
                new DoInvocation());
        objects[1] = Proxy.newProxyInstance(JdkProxyMain.class.getClassLoader(),
                new Class<?>[]{IProxy2.class, IProxy.class},
                new DoInvocation());
        return objects;
    }

    /**
     * 两个重复的接口
     * <p>
     * 会报错
     *
     * @return
     */
    public static final Object sameInterfaces() {
        return Proxy.newProxyInstance(JdkProxyMain.class.getClassLoader(),
                new Class<?>[]{IProxy.class, IProxy.class},
                new DoInvocation());
    }

    /**
     * 没有方法的空接口
     * <p>
     * 只代理equals/hashCode/toString方法, 不过还是会实现接口
     *
     * @return
     */
    public static final Object emptyMethod() {
        return Proxy.newProxyInstance(JdkProxyMain.class.getClassLoader(),
                new Class<?>[]{IEmptyProxy.class},
                new DoInvocation());
    }
}