package cn.joruachan.study.springproxy.declareparent;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 切面
 *
 * @author JoruaChan
 */
@Aspect
@Component
public class BaseAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseAspect.class);

    /**
     * 引入IExpand接口
     */
    @DeclareParents(value = "cn.joruachan.study.springproxy.declareparent..*",
            defaultImpl = ExpandImpl.class)
    public IExpand expand;

    @Before("execution(* cn.joruachan.study.springproxy.declareparent..*())")
    public void before(){
        LOGGER.info("方法执行前.....");
    }

    @After("bean(baseImpl) && this(expand)")
    public void after(IExpand expand){
        LOGGER.info("方法执行后.....");
        expand.expand();
    }
}
