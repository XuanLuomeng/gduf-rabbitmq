package com.gduf.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author LuoXuanwei
 * @date 2023/9/19 17:38
 * 此类为连接工厂创建信道的工具
 */
public class RabbitMqUtils {
    //得到一个连接的channel
    public static Channel getChannel()throws Exception{
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.190.100");
        factory.setUsername("admin");
        factory.setPassword("123");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        return channel;
    }
}
