package com.gduf.rabbitmq.three;

import com.gduf.rabbitmq.utils.RabbitMqUtils;
import com.gduf.rabbitmq.utils.SleepUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author LuoXuanwei
 * @date 2023/9/19 21:39
 */
public class Test {
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
        System.out.println("C1等待接收消息...");
        DeliverCallback deliverCallback = (consumerTag,delivery)->{
            String message = new String(delivery.getBody(),"UTF-8");
            SleepUtils.sleep(10);
            System.out.println("接收到消息:"+message);
            //1.消息标记tag
            //2.false代表只应答接收到的哪个传递的消息 true为应答所有消息包括传递过来的消息
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        };
        //手动应答
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME,autoAck,deliverCallback,consumerTag->{});
    }
}
