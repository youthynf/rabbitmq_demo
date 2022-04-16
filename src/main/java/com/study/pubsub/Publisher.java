package com.study.pubsub;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.util.RabbitMQConnectionUtil;
import org.junit.Test;

/**
 * @ClassName: Publisher
 * @Description:
 * @Author: ynf
 * @Date: 2022-04-16 23:38
 * @Version: 1.0
 */
public class Publisher {

    public static final String EXCHANGE_NAME = "pubsub";

    public static final String QUEUE_NAME1 = "pubsub-one";

    public static final String QUEUE_NAME2 = "pubsub-two";

    @Test
    public void publish() throws Exception {
        // 1.获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();

        // 2.构建channel
        Channel channel = connection.createChannel();

        // 3.构建交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        // 4.构建队列
        channel.queueDeclare(QUEUE_NAME1, false, false, false, null);
        channel.queueDeclare(QUEUE_NAME2, false, false, false, null);

        // 5.绑定交换机和队列，使用的是FANOUT类型的交换机，绑定方式是直接绑定
        channel.queueBind(QUEUE_NAME1, EXCHANGE_NAME, "");
        channel.queueBind(QUEUE_NAME2, EXCHANGE_NAME, "");

        // 6.消息发送到交换机
        channel.basicPublish(EXCHANGE_NAME, "suiyixie", null, "publish/subscribe!".getBytes());
        System.out.println("消息发送成功！");
    }
}
