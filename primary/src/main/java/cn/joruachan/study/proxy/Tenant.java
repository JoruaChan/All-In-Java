package cn.joruachan.study.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 租客
 *
 * @author JoruaChan
 */
public class Tenant {

    /**
     * 了解到的房子
     */
    private List<String> house = new ArrayList<>();

    public Landlord getAgent(Landlord landlord) {
        return (Landlord) Proxy.newProxyInstance(landlord.getClass().getClassLoader(),
                landlord.getClass().getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println(method.getName());
                        return method.invoke(landlord, args);
                    }
                });
    }

    public static void main(String[] args) {
//        // 目标对象
//        Landlord landlord = new Landlord() {
//            @Override
//            public void publish(String message) {
//                System.out.println("message");
//            }
//
//            @Override
//            public void signAgreement() {
//                System.out.println("signAgreement");
//            }
//        };
//
//        Landlord agent = new Tenant().getAgent(landlord);
//        agent.publish("12343");


        List<Tenant> tenants = new ArrayList<>();
        long timestamp = System.currentTimeMillis();
        while (System.currentTimeMillis() - timestamp < 60000 && true) {
            tenants.add(new Tenant());
        }

        Iterator<Tenant> iterator = tenants.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }
}
