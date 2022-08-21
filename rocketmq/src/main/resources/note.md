# RocketMQ使用Note

## 消息类型

### 顺序消息
顺序消息，按照生产者发送消息的顺序消费消息；需要保证生产者单线程生产消息，然后按序保存，并且被消费者单线程消费；

通过分布式锁+本地锁实现顺序消费；

#### 全局顺序消息
在RocketMQ中，可以通过topic指定只有唯一一个queue，然后生产者单线程生产消息，消费者单线程消费消息；从而保证消息生产时只有一个，消息只存到topic下某一个queue，最后消费者单线程从这个queue中获取消息进行消费；

#### 分区局部顺序消息

#### Note
MessageListenerOrderly, 使用了很多的锁，降低了吞吐量；
前一个消息消费阻塞时后面消息都会被阻塞。如果遇到消费失败的消息，会自动对当前消息进行重试（每次间隔时间为1秒），无法自动跳过，重试最大次数是Integer.MAX_VALUE，这将导致当前队列消费暂停，因此通常需要设定有一个最大消费次数，以及处理好所有可能的异常情况；

## 消费者消费模式
在 **_消费者侧_** 设置的消费模式！

### 广播模式 
广播消息，所有消息者都能消费到某条消息；

### 集群模式
集群消息，同一个Group下的多个消费者，消费逻辑完全相同的消费者（包括TAG）会分摊消费消息。一条消息只会投递到一个消费者；**Queue数和Consumer数互成倍数，才会平均负载均衡；**

#### Feature
* Topic下的每一个Queue只会被一个Consumer单独消费；但是一个Consumer可以消费多个Queue；
* 不保证每一次失败重投的消息路由到同一台机器上， 因此处理消息时不应该做任何确定性假设；

### FQA
Q1. 相同Group的Consumer，有的消费模式采用广播，有的采用集群，效果会如何？

---
Q2. 消费模式设置成广播，消息监听采用顺序消费，效果如何？
A2. 广播消息模式，和顺序消费是相悖的。**_如果消息广播出去，就没法做到顺序消费。_**所以，MessageListenerOrderly和MessageModel.BROADCAST不能同时设置于消费者；
--- 

## SpringBoot使用
1. RocketMQMessageListener注解的类，会对应创建一个消费者；如果topic或者tag不定，则不适合使用注解；
2. RocketMQMessageListener注解的类，同时要实现RocketMQListener接口，注解表明消费者，接口表明如何消费；RocketMQ SpringBoot Starter的集成使得无需实现取消息的逻辑；
3. 