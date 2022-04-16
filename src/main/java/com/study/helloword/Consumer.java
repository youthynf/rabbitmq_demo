package com.study.helloword;

import com.rabbitmq.client.*;
import com.study.util.RabbitMQConnectionUtil;
import org.junit.Test;

import java.io.IOException;

/**
 * @ClassName: Consumer
 * @Description: 消费者
 * @Author: ynf
 * @Date: 2022-04-16 22:55
 * @Version: 1.0
 */
public class Consumer {

    @Test
    public void consume() throws Exception {
        // 1.获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();

        // 2.构建channel
        Channel channel = connection.createChannel();

        // 3.构建队列
        channel.queueDeclare(Publisher.QUEUE_NAME, false, false, false, null);

        // 4.监听消息
        channel.basicConsume(Publisher.QUEUE_NAME, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者获取到消息：" + new String(body,"UTF-8"));
            }
        });
        System.out.println("开始监听队列");

        System.in.read();
    }
}
