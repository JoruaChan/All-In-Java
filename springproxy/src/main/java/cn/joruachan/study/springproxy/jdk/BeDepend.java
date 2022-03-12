package cn.joruachan.study.springproxy.jdk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 依赖类
 *
 * @author JoruaChan
 */
@Component
public class BeDepend {
    /**
     * 不能直接使用实现类进行依赖注入
     * 因为IoC容器中这个对象是Proxy的子类，而不是实现类
     */
//    @Autowired
//    BeProxied beProxied;

    @Autowired
    @Qualifier(value = "beProxied")
    IProxy beProxied;

    public void depend() {
        beProxied.execute();
    }
}
