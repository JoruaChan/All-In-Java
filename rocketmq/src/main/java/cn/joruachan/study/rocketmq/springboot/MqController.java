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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
        ZonedDateTime zonedDateTime = LocalDateTime.now().atZone(ZoneId.of("Asia/Shanghai"));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss");
        String dateTimeStr = timeFormatter.format(zonedDateTime);

        String str = "Topic: " + topic + ",TAG: " + tag + ", Time is: " + dateTimeStr;
        Message message = new Message(topic, tag, str.getBytes(StandardCharsets.UTF_8));
        SendResult sendResult = producer.send(message);
        return sendResult;
    }

}
