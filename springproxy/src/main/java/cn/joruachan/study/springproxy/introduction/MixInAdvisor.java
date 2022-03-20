package cn.joruachan.study.springproxy.introduction;

import org.springframework.aop.support.DefaultIntroductionAdvisor;
import org.springframework.stereotype.Component;

/**
 * 完成MixIn锁在AOP工作中，需要的Advisor，即Advice和Pointcut集合
 *
 * @author JoruaChan
 */
@Component
public class MixInAdvisor extends DefaultIntroductionAdvisor {

    public MixInAdvisor() {
        super(new MixInLock(), Lockable.class);
    }
}
