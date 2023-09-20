package com.gduf.rabbitmq.two;

import com.gduf.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @author LuoXuanwei
 * @date 2023/9/19 17:55
 * 生产者 发送大量的消息
 */
public class Task01 {
    //队列名称
    public static final String QUEUE_NAME = "hello";

    //发送大量消息
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //从控制台当中接收消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("发送消息完成:" + message);
        }
    }
}
