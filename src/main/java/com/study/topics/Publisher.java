package com.study.topics;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.util.RabbitMQConnectionUtil;
import org.junit.Test;

/**
 * @ClassName: Publisher
 * @Description:
 * @Author: ynf
 * @Date: 2022-04-17 22:13
 * @Version: 1.0
 */
public class Publisher {

    public static final String EXCHANGE_NAME = "topic";

    public static final String QUEUE_NAME1 = "topic-one";

    public static final String QUEUE_NAME2 = "topic-two";

    @Test
    public void publish() throws Exception {
        // 1.获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();

        // 2.创建channel
        Channel channel = connection.createChannel();

        // 3.构建交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        // 4.构建队列
        channel.queueDeclare(QUEUE_NAME1, false, false, false, null);
        channel.queueDeclare(QUEUE_NAME2, false, false, false, null);

        // 5.绑定交换机和队列
        // TOPIC类型交换机在队列绑定是，需要以aaa.bbb.ccc来编写RoutingKey
        // 其中有两个特殊字符：*（相当于占位符）、#（相当于通配符）
        channel.queueBind(QUEUE_NAME1, EXCHANGE_NAME, "*.orange.*");
        channel.queueBind(QUEUE_NAME2, EXCHANGE_NAME, "*.*.rabbit");
        channel.queueBind(QUEUE_NAME2, EXCHANGE_NAME, "lazy.#");

        // 6.发送消息到交换机
        channel.basicPublish(EXCHANGE_NAME, "big.orange.rabbit", null, "大橙兔子！".getBytes()); // 进入队列1和2
        channel.basicPublish(EXCHANGE_NAME, "small.white.rabbit", null, "小白兔子！".getBytes()); // 进入队列2
        channel.basicPublish(EXCHANGE_NAME, "lazy.dog.dog.dog.dog.dog", null, "懒狗狗狗狗狗~".getBytes()); // 进入队列2

        System.out.println("消息成功发送！");
    }
}
