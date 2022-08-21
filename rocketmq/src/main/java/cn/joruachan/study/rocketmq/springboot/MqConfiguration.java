package cn.joruachan.study.rocketmq.springboot;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
@Configuration
public class MqConfiguration {

    @Bean(RocketMQAutoConfiguration.CONSUMER_BEAN_NAME)
    public DefaultLitePullConsumer defaultLitePullConsumer(RocketMQProperties rocketMQProperties) throws MQClientException {
        RocketMQProperties.Consumer consumerConfig = rocketMQProperties.getConsumer();
        String nameServer = rocketMQProperties.getNameServer();
        String groupName = consumerConfig.getGroup();
        String topicName = consumerConfig.getTopic();

        DefaultLitePullConsumer litePullConsumer = RocketMQUtil.createDefaultLitePullConsumer(nameServer,
                rocketMQProperties.getAccessChannel(), "push_consumer", "real_topic",
                org.apache.rocketmq.spring.annotation.MessageModel.CLUSTERING, SelectorType.TAG, "real_tag",
                consumerConfig.getAccessKey(), consumerConfig.getSecretKey(),
                consumerConfig.getPullBatchSize(), consumerConfig.isTlsEnable());

        return litePullConsumer;
    }

//    @Bean
//    public DefaultMQProducer defaultMQProducer() throws MQClientException {
//        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("ProducerGroup");
//        defaultMQProducer.setNamesrvAddr("192.168.0.4:9876");
//        defaultMQProducer.start();
//        return defaultMQProducer;
//    }


    @Bean
    public DefaultMQPushConsumer defaultMQPushConsumer() throws MQClientException {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("push_consumer");
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.setNamesrvAddr("192.168.0.4:9876");
        defaultMQPushConsumer.subscribe("real_topic", "real_tag");
        defaultMQPushConsumer.registerMessageListener(new MessageListenerOrderly() {

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                for (MessageExt messageExt : msgs) {
                    System.out.println("MessageListenerOrderly:::" + new String(messageExt.getBody(), StandardCharsets.UTF_8));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        defaultMQPushConsumer.start();
        return defaultMQPushConsumer;
    }

}
