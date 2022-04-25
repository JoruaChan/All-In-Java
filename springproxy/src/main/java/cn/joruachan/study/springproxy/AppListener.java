package cn.joruachan.study.springproxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
@Component
public class AppListener implements ApplicationListener<ApplicationEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppListener.class);

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        LOGGER.info("接收到事件:{}",event);
    }
}
