package com.gduf.rabbitmq.two;

import com.gduf.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author LuoXuanwei
 * @date 2023/9/19 17:41
 * 这是一个工作线程(相当于之前消费者)
 */
public class Worker01 {
    //队列名称
    public static final String QUEUE_NAME = "hello";

    //接收消息
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //消息接收
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("接收到的消息:" + new String(message.getBody()));
        };
        //消息接受被取消
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println(consumerTag + "消息者取消消费接口回调逻辑");
        };
        System.out.println("C2等待接收消息...");
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
