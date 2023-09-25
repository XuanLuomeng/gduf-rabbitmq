package com.gduf.rabbitmq.controller;

import com.gduf.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author LuoXuanwei
 * @date 2023/9/22 22:19
 * 开始发消息     测试确认
 */
@Slf4j
@Configuration
@RequestMapping("/confirm")
public class ProducerController {
    @Resource
    private RabbitTemplate rabbitTemplate;

    //发消息
    @GetMapping("/sendMessage/{message}")
    public void sendMessage(@PathVariable String message) {
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,
                ConfirmConfig.CONFIRM_ROUTING_KEY);
        log.info("发送消息内容:{}", message);
    }
}
