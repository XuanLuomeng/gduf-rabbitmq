package com.gduf.rabbitmq.consumer;

import com.gduf.rabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author LuoXuanwei
 * @date 2023/9/22 17:21
 * 消费者 基于插件的延迟消息
 */
@Slf4j
@Component
public class DelayQueueConsumer {
    //监听消息
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiveDelayQueue(Message message) {
        String msg = new String(message.getBody());
        log.info("当前时间:{}，收到延迟队列的消息是:{}", new Date().toString(), msg);
    }
}
