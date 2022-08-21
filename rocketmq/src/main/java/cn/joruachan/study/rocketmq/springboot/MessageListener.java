package cn.joruachan.study.rocketmq.springboot;

import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
@Component
@RocketMQMessageListener(topic = "real_topic", consumerGroup = "push_consumer", selectorType = SelectorType.TAG, selectorExpression = "real_tag", messageModel = MessageModel.CLUSTERING)
public class MessageListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.println("MessageListener:::" + message);
    }
}
