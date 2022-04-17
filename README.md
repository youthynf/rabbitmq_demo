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
- 构建一个自定义的交换机，并指定类型为FANOUT；
- 让交换机和多个队列绑定到一起；
- 不同的消费者监听不同队列的消息；
- 生产者发布的消息将会到达每一个绑定的队列中，即一个消息会达到多个队列，并给多个消费者消费，最终实现发布订阅模式。

### 4.Routing
- 生产者定义好类型为DIRECT的Exchange；
- 生产者在绑定Exchange和Queue时，需要指定好RoutingKey；
- 生产者发送消息时也需要指定好RoutingKey；
- 只有消息的RoutingKey与队列Queue的RoutingKey一致时，这个消息才会被路由到对应的队列Queue中；
- 消费者和之前的模式一样，直接从指定队列Queue中消费消息即可。

### 5.Topic
- 生产者指定TOPIC类型Exchange；
- 生产者在绑定Exchange和Queue时，使用形如“xxx.xxx.xxx”的RoutingKey;
- 生产者发送消息时，使用形式符合的RoutingKey才能进入正确的队列；
- 消费者和之前模式一样，直接从指定的队列中消费消息即可。

### 6.RPC
- 通过RPC模式，可使用RabbitMQ实现客户端与服务端解耦；
- 需要让客户端发送消息时携带两个属性：
    1. replyTo：告诉服务端将响应消息存放到的队列名称；
    2. correctionId：告诉服务端发送相关消息时，需要携带位置标志来告诉客户端认领；