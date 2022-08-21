package cn.joruachan.study.rocketmq.springboot;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
@RestController
public class MqController {

    @Autowired
    private DefaultMQProducer producer;

    @PostMapping("/send")
    public SendResult sendMessage(String topic, String tag) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        String str = "Topic: " + topic + ",TAG: " + tag + ", Time is: " + System.currentTimeMillis();
        Message message = new Message(topic, tag, str.getBytes(StandardCharsets.UTF_8));
        SendResult sendResult = producer.send(message);
        return sendResult;
    }

}
