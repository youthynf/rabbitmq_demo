package com.study.routing;

import com.rabbitmq.client.*;
import com.study.util.RabbitMQConnectionUtil;
import org.junit.Test;

import java.io.IOException;

/**
 * @ClassName: Consumer
 * @Description: 消费者：让消费者关闭自动ack，并且设置消息的流控，最终实现消费者可以尽可能去多消费消息。
 * @Author: ynf
 * @Date: 2022-04-16 23:12
 * @Version: 1.0
 */
public class Consumer {

    @Test
    public void consume1() throws Exception {
        // 1.获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();

        // 2.构建channel
        final Channel channel = connection.createChannel();

        // 3.构建队列
        channel.queueDeclare(Publisher.QUEUE_NAME1, false, false, false, null);

        // 4.设置消息的流控
        channel.basicQos(3);

        // 5.监听消息
        DefaultConsumer callback = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("消费者1号-获取到消息：" + new String(body, "UTF-8"));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(Publisher.QUEUE_NAME1, false, callback);
        System.out.println("开始监听队列消息");

        System.in.read();
    }

    @Test
    public void consume2() throws Exception {
        // 1.获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();

        // 2.构建channel
        final Channel channel = connection.createChannel();

        // 3.构建队列
        channel.queueDeclare(Publisher.QUEUE_NAME2, false, false, false, null);

        // 4.设置消息的流控
        channel.basicQos(3);

        // 5.监听消息
        DefaultConsumer callback = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("消费者2号-获取到消息：" + new String(body, "UTF-8"));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(Publisher.QUEUE_NAME2, false, callback);
        System.out.println("开始监听队列消息");

        System.in.read();
    }
}
