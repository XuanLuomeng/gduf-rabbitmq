package com.gduf.rabbitmq.three;

import com.gduf.rabbitmq.utils.RabbitMqUtils;
import com.gduf.rabbitmq.utils.SleepUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author LuoXuanwei
 * @date 2023/9/19 21:28
 * 消息在手动应答时是不丢失、放回队列中重新消费
 */
public class Worker04 {
    //队列名称
    public static final String TASK_QUEUE_NAME = "ack_queue";

    //接收消息
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C2等待接收消息处理时间较长");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            //沉睡30s
            SleepUtils.sleep(30);
            System.out.println("接收到的消息:" + new String(message.getBody(), "UTF-8"));
            //手动应答
            /**
             * 1.消息的标记 tag
             * 2.是否批量应答 false不批量应答信道中的消息 true:批量
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };

        //采用手动应答
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, (consumerTag -> {
            System.out.println(consumerTag + "消费者取消消费接口回调逻辑");
        }));
    }
}

