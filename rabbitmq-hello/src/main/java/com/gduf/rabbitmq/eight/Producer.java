package com.gduf.rabbitmq.eight;

import com.gduf.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;

/**
 * @author LuoXuanwei
 * @date 2023/9/21 16:19
 * 死信队列之生产者代码
 */
public class Producer {
    //普通交换机名称
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //死信消息 设置ttl时间 time to live 单位是ms 10000ms = 10s
        //AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("10000").build();
        for (int i = 0; i < 11; i++) {
            String message = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE, "zhangsan", null, message.getBytes(StandardCharsets.UTF_8));
        }
    }
}