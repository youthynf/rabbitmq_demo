package com.study.rpc;

import com.rabbitmq.client.*;
import com.study.util.RabbitMQConnectionUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName: Publisher
 * @Description:
 * @Author: ynf
 * @Date: 2022-04-17 22:32
 * @Version: 1.0
 */
public class Publisher {

    public static final String QUEUE_PUBLISHER = "rpc_publisher";

    public static final String QUEUE_CONSUMER = "rpc_consumer";

    @Test
    public void publish() throws Exception {
        // 1.获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();

        // 2.构建Channel
        final Channel channel = connection.createChannel();

        // 3.构建队列
        channel.queueDeclare(QUEUE_PUBLISHER,false,false,false,null);
        channel.queueDeclare(QUEUE_CONSUMER,false,false,false,null);

        // 4.发布消息
        String message = "这是客户端请求消息，Hello RPC!";
        final String uuid = UUID.randomUUID().toString();
        AMQP.BasicProperties properties = new AMQP.BasicProperties()
                .builder()
                .replyTo(QUEUE_CONSUMER)
                .correlationId(uuid)
                .build();

        channel.basicPublish("", QUEUE_PUBLISHER, properties, message.getBytes());

        channel.basicConsume(QUEUE_CONSUMER, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String id = properties.getCorrelationId();
                if (id != null && id.equalsIgnoreCase(uuid)) {
                    System.out.println("接收到服务端的响应：" + new String(body, "UTF-8"));
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });

        System.out.println("消息发送成功！");
        System.in.read();
    }

}
