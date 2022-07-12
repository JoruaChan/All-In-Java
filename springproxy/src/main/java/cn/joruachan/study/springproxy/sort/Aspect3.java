package cn.joruachan.study.springproxy.sort;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Aspect3
 *
 * @author JoruaChan
 */
@Aspect
@Component
@Order(1)
public class Aspect3
        // implements Ordered
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Aspect3.class);

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

//    @Override
//    public int getOrder() {
//        return 1;
//    }
}
