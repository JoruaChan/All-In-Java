package cn.joruachan.study.springproxy.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 排序的主函数
 *
 * @author JoruaChan
 */
@Configuration
@EnableAspectJAutoProxy
public class SortMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(SortMain.class);

    // 为什么目标方法必须要public？？

    /**
     * 对比1: 不使用任何注解、接口
     * 对比2: 使用Ordered接口
     * 对比3：使用@Order注解
     * 对比4：Aspect同时使用Ordered接口和@Order注解
     * 对比5：Aspect内方法执行顺序
     * <p>
     * 1. 获取Advisor所在Aspect类的 {@linkplain org.springframework.core.Ordered#getOrder}
     * 2. 获取Advisor所在Aspect类的 {@linkplain org.springframework.core.annotation.Order} 注解值
     * 3. 同Aspect类的Advisor，按照方法名排序
     * 4. 同Order值不同Aspect，按照getBean顺序
     *
     * @param args
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext("cn.joruachan.study.springproxy.sort");

        SortTarget sortTarget = applicationContext.getBean(SortTarget.class);
        sortTarget.test1();
    }
}
