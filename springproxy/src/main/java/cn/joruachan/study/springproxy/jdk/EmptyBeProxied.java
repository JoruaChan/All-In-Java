package cn.joruachan.study.springproxy.jdk;

import java.io.Serializable;

/**
 * 只实现EmptyInterface接口,没有方法的接口!
 *
 * !!!!!!!!!!!
 * 1. 如果接口没有方法（如Serializable），Spring是不会使用Jdk代理!!!
 * 2. 类中如果没有方法，不会生成代理，而使用原类!!!
 *
 * @author JoruaChan
 */
public class EmptyBeProxied implements EmptyInterface {

    /**
     * 由于AOP拦截了方法，但是接口又是没有方法的，所以需要生成Cglib代理类处理；
     * 如果这个类没有任何方法，不会生成代理对象
     */
    public void sayHello(){
        System.out.println("hello");
    }
}
