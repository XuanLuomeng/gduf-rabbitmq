package com.gduf.rabbitmq.controller;

import com.gduf.rabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author LuoXuanwei
 * @date 2023/9/21 21:17
 * 发送延迟消息
 */
@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMsgController {
    @Resource
    private RabbitTemplate rabbitTemplate;

    //开始发消息
    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message) {
        log.info("当前时间:{},发送一条信息给两个ttl队列:{}", new Date().toString(), message);
        rabbitTemplate.convertAndSend("X", "XA", "消息来自ttl为10s的队列:" + message);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自ttl为40s的队列:" + message);
    }

    //开始发消息  消息  ttl
    @GetMapping("/sendExpirationMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message, @PathVariable String ttlTime) {
        log.info("当前时间:{},发送一条时长{}ms的ttl信息给QC队列:{}", new Date().toString(), ttlTime, message);
        rabbitTemplate.convertAndSend("X", "XC", message, msg -> {
            //发送消息的时候 延迟时长
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });
    }

    //开始发消息,基于插件的 消息 及 延迟的时间
    @GetMapping("/sendDelayMsg/{message}/{delayTime}")
    public void sendMsg(@PathVariable String message,@PathVariable Integer delayTime){
        log.info("当前时间:{},发送一条时长{}ms的信息给延迟队列delayed.queue:{}", new Date().toString(), delayTime, message);
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME,
                DelayedQueueConfig.DELAYED_ROUTING_KEY, message, msg -> {
            //发送消息的时候 延迟时长 单位:ms
            msg.getMessageProperties().setDelay(delayTime);
            return msg;
        });
    }
}
