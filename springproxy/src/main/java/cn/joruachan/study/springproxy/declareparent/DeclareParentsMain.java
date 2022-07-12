package cn.joruachan.study.springproxy.declareparent;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
@EnableAspectJAutoProxy
@Configuration
public class DeclareParentsMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(
                        "cn.joruachan.study.springproxy.declareparent");

        ((IBase)applicationContext.getBean("baseImpl")).base();

        // 为BaseImpl类拓展方法
        ((IExpand)applicationContext.getBean("baseImpl")).expand();
    }
}
