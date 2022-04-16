package com.study.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @ClassName: RabbitMQConnectionUtil
 * @Description:
 * @Author: ynf
 * @Date: 2022-04-16 22:19
 * @Version: 1.0
 */
public class RabbitMQConnectionUtil {

    public static final String RABBITMQ_HOST = "192.168.93.129";

    public static final int RABBITMQ_PORT = 5672;

    public static final String RABBITMQ_USERNAME = "guest";

    public static final String RABBITMQ_PASSWORD = "guest";

    public static final String RABBITMQ_VIRTUAL_HOST = "/";

    /**
     * 构建RabbitMQ的连接对象
     */
    public static Connection getConnection() throws Exception {
        // 1.创建Connection工厂
        ConnectionFactory factory = new ConnectionFactory();

        // 2.设置RabbitMQ的连接信息
        factory.setHost(RABBITMQ_HOST);
        factory.setPort(RABBITMQ_PORT);
        factory.setUsername(RABBITMQ_USERNAME);
        factory.setPassword(RABBITMQ_PASSWORD);
        factory.setVirtualHost(RABBITMQ_VIRTUAL_HOST);

        // 3.返回连接对象
        return factory.newConnection();
    }
}
