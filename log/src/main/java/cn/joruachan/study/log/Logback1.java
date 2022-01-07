package cn.joruachan.study.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
public class Logback1 {
    private static final Logger logger = LoggerFactory.getLogger(Logback1.class);

    private int count = 0;

    public void output() {
        logger.debug("Logback1, current:{}", count++);
    }
}
