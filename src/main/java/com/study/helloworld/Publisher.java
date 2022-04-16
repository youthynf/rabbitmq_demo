package com.study.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.util.RabbitMQConnectionUtil;
import org.junit.Test;

/**
 * @ClassName: Publisher
 * @Description: 生产者
 * @Author: ynf
 * @Date: 2022-04-16 22:51
 * @Version: 1.0
 */
public class Publisher {

    public static final String QUEUE_NAME = "hello";

    @Test
    public void publish() throws Exception {
        // 1.获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();

        // 2.构建channel
        Channel channel = connection.createChannel();

        // 3.构建队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 4.发布消息
        String message = "Hello world!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("消息发送成功！");
    }
}
