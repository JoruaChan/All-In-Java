package cn.joruachan.study.springproxy.sort;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Aspect1
 *
 * @author JoruaChan
 */
@Aspect
@Component
//@Order(1)
public class Aspect1
        implements Ordered
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Aspect1.class);

    @Before("execution(* cn.joruachan.study.springproxy.sort.SortTarget.test1())")
//    public void beforeTest1(JoinPoint joinPoint) {
    public void aBeforeTest1(JoinPoint joinPoint) {
        LOGGER.info("Method: beforeTest1, Current execution is : {}", joinPoint.getSignature().getName());
    }

    @Before("execution(* cn.joruachan.study.springproxy.sort.SortTarget.test*())")
    public void before(JoinPoint joinPoint) {
        LOGGER.info("Method: before, Current execution is : {}", joinPoint.getSignature().getName());
    }

    @Around("execution(* cn.joruachan.study.springproxy.sort.SortTarget.test*())")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        LOGGER.info("Method: around, before joinPoint, Current execution is : {}", joinPoint.getSignature().getName());
        joinPoint.proceed();
        LOGGER.info("Method: around, after joinPoint, Current execution is : {}", joinPoint.getSignature().getName());
    }

    @After("execution(* cn.joruachan.study.springproxy.sort.SortTarget.test*())")
    public void after(JoinPoint joinPoint) {
        LOGGER.info("Method: after, Current execution is : {}", joinPoint.getSignature().getName());
    }

    @AfterReturning("execution(* cn.joruachan.study.springproxy.sort.SortTarget.test*())")
    public void afterReturning(JoinPoint joinPoint) {
        LOGGER.info("Method: afterReturning, Current execution is : {}", joinPoint.getSignature().getName());
    }

    @AfterThrowing("execution(* cn.joruachan.study.springproxy.sort.SortTarget.test*())")
    public void afterThrowing(JoinPoint joinPoint) {
        LOGGER.info("Method: afterThrowing, Current execution is : {}", joinPoint.getSignature().getName());
    }


    @Override
    public int getOrder() {
        return 1;
    }
}
