package cn.joruachan.study.log;

import cn.joruachan.study.log.special.SpecialLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
public class LogbackMain {
    private static final Logger logger = LoggerFactory.getLogger(LogbackMain.class);

    public static void main(String[] args) {
        logger.info("开始！！！");

        Logback1 logback1 = new Logback1();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                logback1.output();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "logback-1").start();

        Logback2 logback2 = new Logback2();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                logback2.output();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "logback-2").start();

        SpecialLog specialLog = new SpecialLog();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                specialLog.logDebug();
                specialLog.logWarn();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "special-log").start();
    }
}
