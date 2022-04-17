package com.study.rpc;

import com.rabbitmq.client.*;
import com.study.util.RabbitMQConnectionUtil;
import org.junit.Test;

import java.io.IOException;

/**
 * @ClassName: Consumer
 * @Description:
 * @Author: ynf
 * @Date: 2022-04-17 22:47
 * @Version: 1.0
 */
public class Consumer {

    @Test
    public void consume() throws Exception {
        // 1.获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();

        // 2.构建Channel
        final Channel channel = connection.createChannel();

        // 3. 构建队列
        channel.queueDeclare(Publisher.QUEUE_PUBLISHER,false,false,false,null);
        channel.queueDeclare(Publisher.QUEUE_CONSUMER,false,false,false,null);

        // 4.监听消息
        channel.basicConsume(Publisher.QUEUE_PUBLISHER, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("服务端收到消息：" + new String(body, "UTF-8"));
                String response = "收到了客户端发出来的请求，这里是响应消息";
                String replyQueueName = properties.getReplyTo();
                String uuid = properties.getCorrelationId();
                AMQP.BasicProperties props = new AMQP.BasicProperties()
                        .builder()
                        .correlationId(uuid)
                        .build();
                channel.basicPublish("", replyQueueName, props, response.getBytes());
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });

        System.out.println("开始监听队列");
        System.in.read();
    }
}
