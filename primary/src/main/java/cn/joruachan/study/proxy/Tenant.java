package cn.joruachan.study.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 租客
 *
 * @author JoruaChan
 */
public class Tenant {




    public static void main(String[] args) {
        Proxy.newProxyInstance(Landlord.class.getClassLoader(),
                Landlord.class.getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return null;
                    }
                });
    }
}
