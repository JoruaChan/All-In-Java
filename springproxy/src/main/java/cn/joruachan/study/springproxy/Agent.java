package cn.joruachan.study.springproxy;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Landlord的代理<br>
 * Agent并不是Landlord的直接代理对象，只是在此处声明。
 * 实际的代理对象是Spring帮忙生成的。
 *
 * @author JoruaChan
 */
@Aspect
public class Agent {

    @Pointcut("execution(* cn.joruachan.study.springproxy.Landlord.*(..))")
    public void pointCut1() {
    }

    @Before("pointCut1()")
    public void before(){
        System.out.println("代理执行前.....");
    }

    @Pointcut("execution(* cn.joruachan.study.springproxy.JdkProxyTestImpl.*(..))")
    public void pointCut2() {
    }

    @After("pointCut2()")
    public void after(){
        System.out.println("代理执行后.......");
    }
}
