package com.study.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.util.RabbitMQConnectionUtil;
import org.junit.Test;

/**
 * @ClassName: Publisher
 * @Description:
 * @Author: ynf
 * @Date: 2022-04-17 21:31
 * @Version: 1.0
 */
public class Publisher {

    public static final String EXCHANGE_NAME = "routing";

    public static final String QUEUE_NAME1 = "routing-one";

    public static final String QUEUE_NAME2 = "touting-two";

    @Test
    public void publish() throws Exception {
        // 1.获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();

        // 2.构建channel
        Channel channel = connection.createChannel();

        // 3.构建交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        // 4.构建队列
        channel.queueDeclare(QUEUE_NAME1, false, false, false, null);
        channel.queueDeclare(QUEUE_NAME2, false, false, false, null);

        // 5.绑定交换机和队列
        channel.queueBind(QUEUE_NAME1, EXCHANGE_NAME, "ORANGE");
        channel.queueBind(QUEUE_NAME2, EXCHANGE_NAME, "BLACK");
        channel.queueBind(QUEUE_NAME2, EXCHANGE_NAME, "GREEN");

        // 6.发消息到交换机
        channel.basicPublish(EXCHANGE_NAME, "ORANGE", null, "大橙子！".getBytes());
        channel.basicPublish(EXCHANGE_NAME, "BLACK", null, "黑布林大狸子！".getBytes());
        channel.basicPublish(EXCHANGE_NAME, "GREEN", null, "小白兔！".getBytes());
        System.out.println("消息成功发送！");
    }
}
