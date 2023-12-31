package com.gduf.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LuoXuanwei
 * @date 2023/9/22 22:06
 * 配置类     发布确认       高级
 */
@Configuration
public class ConfirmConfig {
    //交换机
    public static final String CONFIRM_EXCHANGE_NAME = "confirm_exchange";
    //队列
    public static final String CONFIRM_QUEUE_NAME = "confirm_queue";
    //RoutingKey
    public static final String CONFIRM_ROUTING_KEY = "key1";
    //备份交换机
    public static final String BACKUP_EXCHANGE_NAME = "backup_exchange";
    //备份队列
    public static final String BACKUP_QUEUE_NAME = "backup_queue";
    //报警队列
    public static final String WARNING_QUEUE_NAME = "warning_queue";

    //声明交换机
    @Bean("confirmExchange")
    public DirectExchange confirmExchange() {
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME).durable(true)
                .withArgument("alternate-exchange", BACKUP_EXCHANGE_NAME).build();
    }

    @Bean("confirmQueue")
    public Queue confirmQueue() {
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }

    //绑定
    @Bean
    public Binding queueBindingExchange(@Qualifier("confirmQueue") Queue confirmQueue,
                                        @Qualifier("confirmExchange") DirectExchange confirmExchange) {
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }

    //备份交换机
    @Bean("backupExchange")
    public FanoutExchange backupExchange() {
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    //备份队列
    @Bean("backupQueue")
    public Queue backupQueue() {
        return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
    }

    //报警队列
    @Bean("warningQueue")
    public Queue warningQueue() {
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }

    //备份绑定
    @Bean
    public Binding backupBindingBackupExchange(@Qualifier("backupQueue") Queue backupQueue,
                                               @Qualifier("backupExchange") FanoutExchange backupExchange) {
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }

    //报警绑定
    @Bean
    public Binding warningBindingBackupExchange(@Qualifier("warningQueue") Queue warningQueue,
                                                @Qualifier("backupExchange") FanoutExchange backupExchange) {
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }
}