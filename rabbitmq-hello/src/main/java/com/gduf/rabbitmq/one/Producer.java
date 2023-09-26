package com.gduf.rabbitmq.one;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author LuoXuanwei
 * @date 2023/9/19 17:05
 * 生产者:发消息
 */
public class Producer {
    //队列名称
    public static final String QUEUE_NAME = "hello";

    //发消息
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //工厂IP连接RabbitMQ队列
        factory.setHost("192.168.190.100");
        //用户名
        factory.setUsername("admin");
        //密码
        factory.setPassword("123");
        //创建连接
        Connection connection = factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
        /**
         * 生成队列
         * 1.队列名称
         * 2.队列里面的消息是否持久化(磁盘)默认情况消息存储在内存中
         * 3.该队列是否只供一个消费者进行消费，是否进行消费共享，true可以多个消费者消费 false只能一个消费者消费
         * 4.是否自动删除 最后一个消费者端开连接以后 该队列依据是否自动删除 true自动删除 false不自动删除
         * 5.其它参数
         */
        Map<String, Object> arguments = new HashMap<>();
        //官方允许范围是0-255 但建议设置在0-10之间 因为设置越大越消耗CPU和内存
        arguments.put("x-max-priority", 10);
        channel.queueDeclare(QUEUE_NAME, true, false, false, arguments);
        //发消息(初次使用)
//        String message = "hello world";
        for (int i = 1; i < 11; i++) {
            String message = "info" + i;
            if (i == 5) {
                AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().priority(5).build();
                channel.basicPublish("", QUEUE_NAME, properties, message.getBytes(StandardCharsets.UTF_8));
            } else {
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            }
        }
        /**
         * 发送一个消费
         * 1.发送到那个交换机
         * 2.路由的key值是哪个 本次是队列的名称
         * 3.其它参数信息
         * 4.发送消息的消息体
         */
//        channel.basicPublish("",QUEUE_NAME,null,message.getBytes(StandardCharsets.UTF_8));
        System.out.println("消息发送完毕");
    }
}
