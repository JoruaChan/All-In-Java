package cn.joruachan.study.springproxy;

import org.aspectj.lang.annotation.Around;
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
    public void pointCut() {
    }

    @Before("pointCut()")
    public void around(){
        System.out.println("我是房东的代理，我是要收取费用的哦！");
    }
}
