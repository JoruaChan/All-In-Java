package cn.joruachan.study.springproxy.declareparent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * IExpand实现类
 *
 * @author JoruaChan
 */
//@Component
public class ExpandImpl implements IExpand {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExpandImpl.class);

    @Override
    public void expand() {
        LOGGER.info("I am ExpandImpl!");
    }
}
