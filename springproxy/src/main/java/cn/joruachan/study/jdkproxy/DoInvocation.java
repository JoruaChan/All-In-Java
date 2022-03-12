package cn.joruachan.study.jdkproxy;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 代理类的业务处理类<br>
 * <p>
 * 针对默认方法做了特殊处理
 *
 * @author JoruaChan
 */
public class DoInvocation implements InvocationHandler {

    Map<Method, MethodHandle> methodMap = new ConcurrentHashMap<>();

    /**
     * 被代理对象
     */
    Object target;

    /**
     * 实际项目中不能不初始化target
     */
    public DoInvocation() {
    }

    public DoInvocation(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("toString".equals(method.getName())) {
            return proxy.getClass().getName();
        }

        if (method.isDefault()) {
            // 如果默认方法被重写，这里又想调用默认方法，则需要通过句柄来

            // 获取到调用方法的类
            MethodHandle defaultHandle = methodMap.computeIfAbsent(method, (key) -> {
                // 获取方法对应的句柄
                MethodHandle methodHandle = MethodHandleUtil.methodHandleForMethod(method);
                if (methodHandle != null) {
                    return methodHandle.bindTo(proxy);
                }
                return null;
            });

            if (defaultHandle != null) {
                System.out.println("默认接口方法：" + method.getName());
                return defaultHandle.invokeWithArguments(args);
            }
            return null;
        }

        return method.invoke(target, args);
    }
}
