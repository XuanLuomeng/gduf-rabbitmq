package com.gduf.rabbitmq.eight;

import com.gduf.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LuoXuanwei
 * @date 2023/9/21 16:04
 * 死信队列 实战
 */
public class Consumer02 {
    //死信队列名称
    public static final String DEAD_QUEUE = "normal_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("等待接收消息");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("Consumer02接收的消息是" + new String(message.getBody(), "UTF-8"));
        };
        channel.basicConsume(DEAD_QUEUE, true, deliverCallback, s -> {
        });
    }
}