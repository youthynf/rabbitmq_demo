# rabbitmq_demo

## 基本的通讯模式

### 1.HelloWorld
- 生产者发送消息到默认的交换机，并到达队列，消费者从队列中获取消息，并进行消息消费。

### 2.WorkQueues
- 一个队列中的消息，只会被一个消费者成功消费；
- 默认情况下，RabbitMQ的队列会将消息以轮询的方式交给不同的消费者消费；
- 消费者拿到消息后，需要给RabbitMQ发送一个ack，RabbitMQ认为消费者应拿到消息了；
- 让消费者关闭自动ack，并且设置消息的流控，最终实现消费者可以尽可能去多消费消息；

### 3.Publish/Subscribe
- 构建一个自定义的交换机，并制定类型为FANOUT；
- 让交换机和多个队列绑定到一起；
- 不同的消费者监听不同队列的消息；
- 生产者发布的消息将会到达每一个绑定的队列中，即一个消息会达到多个队列，并给多个消费者消费，最终实现发布订阅模式。