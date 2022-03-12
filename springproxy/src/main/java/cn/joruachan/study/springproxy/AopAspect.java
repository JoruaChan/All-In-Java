package cn.joruachan.study.springproxy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopAspect {

    @Pointcut("execution(* cn.joruachan.study.springproxy.jdk.InnerCall.execute2())")
    public void innerCallPointCut() {
    }

    @Around("innerCallPointCut()")
    public void innerCallAround(ProceedingJoinPoint joinPoint) {
        around(joinPoint);
    }

    @Pointcut("execution(* cn.joruachan.study.springproxy.jdk.BeProxied.execute())")
    public void jdkProxyPointCut() {
    }

    @Around("jdkProxyPointCut()")
    public void jdkProxyAround(ProceedingJoinPoint joinPoint) {
        around(joinPoint);
    }

    /**
     * 非接口方法
     */
    @Pointcut("execution(void cn.joruachan.study.springproxy.jdk.BeProxied.other())")
    public void noInterfaceMethodPointCut() {
    }

    @Around("noInterfaceMethodPointCut()")
    public void noInterfaceMethodAround(ProceedingJoinPoint joinPoint) {
        around(joinPoint);
    }

    public void around(ProceedingJoinPoint joinPoint) {
        System.out.println("JDK代理方法执行前");

        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        System.out.println("JDK代理方法执行后");
    }
}
