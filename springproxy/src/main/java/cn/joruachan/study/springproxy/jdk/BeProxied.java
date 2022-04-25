package cn.joruachan.study.springproxy.jdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 接口代理
 *
 * @author JoruaChan
 */
public class BeProxied implements IProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeProxied.class);

    @Override
    public void execute() {
        LOGGER.info("BeProxied#execute");
    }

    /**
     * 只要实现了IProxy接口，这个方法在Spring中是永远不会被执行到的！
     * 因为生成的代理类不会代理这个方法！！！
     * 就算是指定拦截这个方法，由于不会生成该代理方法，所以依旧执行不到！！
     */
    public void other() {
        LOGGER.info("BeProxied#other");
    }
}
