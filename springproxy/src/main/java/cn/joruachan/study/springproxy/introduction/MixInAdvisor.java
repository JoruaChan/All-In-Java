package cn.joruachan.study.springproxy.introduction;

import cn.joruachan.study.springproxy.jdk.IProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.DefaultIntroductionAdvisor;
import org.springframework.stereotype.Component;

/**
 * 完成MixIn锁在AOP工作中需要的Advisor
 * <p>
 * 通过构造函数，将Advice传入，以及需要引入的接口；
 * 其实就是提供:
 * <ul>
 *     <li>1.addInterface,通过LinkedHashSet保存引入的接口</li>
 *     <li>2.validateInterface,如果是DynamicIntroductionAdvice，需要其实现了所有引入的接口</li>
 * </ul>
 *
 * @author JoruaChan
 */
@Component
public class MixInAdvisor extends DefaultIntroductionAdvisor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MixInAdvisor.class);

    public MixInAdvisor() {
        super(new MixInLock(), Lockable.class);
    }

    @Override
    public boolean matches(Class<?> clazz) {
        // 通过重写此方法，可以定义类是否拦截
        // 如果要精确到某个对象的方法，需用用IntroductionAwareMethodMatcher
        boolean match = IProxy.class.isAssignableFrom(clazz);
        LOGGER.info("class:{}，match:{}", clazz.getSimpleName(), match);
        return match;
    }
}
