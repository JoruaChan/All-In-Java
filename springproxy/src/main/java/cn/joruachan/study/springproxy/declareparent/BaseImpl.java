package cn.joruachan.study.springproxy.declareparent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * IBase接口实现
 *
 * @author JoruaChan
 */
@Component
public class BaseImpl implements IBase{
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseImpl.class);

    @Override
    public void base() {
        LOGGER.info("I am BaseImpl");
    }
}
