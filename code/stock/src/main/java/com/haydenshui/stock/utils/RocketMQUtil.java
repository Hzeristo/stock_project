package com.haydenshui.stock.utils;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class RocketMQUtil {

    private static RocketMQTemplate staticRocketMQTemplate;

    @Autowired
    private RocketMQTemplate rocketMQTemplate; 

    @PostConstruct
    public void init() {
        staticRocketMQTemplate = this.rocketMQTemplate; 
    }

    public static void sendMessage(String topic, String msg) {
        staticRocketMQTemplate.convertAndSend(topic, msg);
    }

    public static void sendMessageWithKey(String topic, String key, String msg) {
        Message<String> message = MessageBuilder.withPayload(msg)
                .setHeader("KEYS", key)
                .build();
        staticRocketMQTemplate.send(topic, message);
    }

    public static void sendMessageWithTag(String topic, String tag, String msg) {
        staticRocketMQTemplate.convertAndSend(topic + ":" + tag, msg);
    }

    public static void sendTransactionMessage(String topic, String transId, String msg) {
        Message<String> message = MessageBuilder.withPayload(msg)
                .setHeader("TRANSACTION_ID", transId)
                .build();
        staticRocketMQTemplate.sendMessageInTransaction(topic, message, transId);
    }
}
