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
    public static void main(String[] args) {
//        // 生成一个代理类
//        IDoSomething proxy = (IDoSomething) Proxy.newProxyInstance(IDoSomething.class.getClassLoader(),
//                new Class[]{IDoSomething.class}, new DoInvocation());
//        System.out.println(proxy);
//        proxy.beforeDo("haha", 12);

        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        Object proxy = Proxy.newProxyInstance(JdkProxyMain.class.getClassLoader(),
                new Class[]{IDo1.class, IDo2.class}, new Invocation());
        System.out.println(proxy);
    }
}

class Ret1 {
}

class Ret2
        extends Ret1 {
}

interface IDo1 {
    Ret1 say(String text);
}

interface IDo2 {
    Ret2 say(String text);
}

class Invocation implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("toString")) {
            return "123";
        }
        return null;
    }
}
