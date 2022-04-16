package com.study.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.util.RabbitMQConnectionUtil;
import org.junit.Test;

/**
 * @ClassName: Publisher
 * @Description: 生产者：与hello world的消费者形式是一样的，都是将消息推送给默认的交换机。
 * @Author: ynf
 * @Date: 2022-04-16 22:51
 * @Version: 1.0
 */
public class Publisher {

    public static final String QUEUE_NAME = "workqueues";

    @Test
    public void publish() throws Exception {
        // 1.获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();

        // 2.构建channel
        Channel channel = connection.createChannel();

        // 3.构建队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 4.发布消息
        for (int i = 0; i < 10; i++) {
            String message = "发布的消息" + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        }
        System.out.println("消息发送成功！");
    }
}
