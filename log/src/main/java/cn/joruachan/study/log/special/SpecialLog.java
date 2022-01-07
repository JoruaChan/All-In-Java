package cn.joruachan.study.log.special;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
public class SpecialLog {
    private static final Logger logger = LoggerFactory.getLogger(SpecialLog.class);

    public void logDebug() {
        logger.debug("SpecialLog.Debug日志");
    }

    public void logWarn() {
        logger.warn("SpecialLog.Warn日志");
    }

}
