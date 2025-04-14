package com.haydenshui.stock.utils;

import java.util.Map;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class RocketMQUtils {

    private static Map<String, RocketMQTemplate> staticTemplateMap;

    @Autowired
    private Map<String, RocketMQTemplate> TemplateMap;

    @PostConstruct
    public void init() {
        staticTemplateMap = TemplateMap;
    }

    public static void sendMessage(String key, String topic, String msg) {
        RocketMQTemplate template = staticTemplateMap.get(key);
        if (template == null) throw new RuntimeException("No RocketMQTemplate found for key: " + key);
        template.convertAndSend(topic, msg);
    }

    public static void sendMessageWithKey(String key, String topic, String msgKey, String msg) {
        RocketMQTemplate template = staticTemplateMap.get(key);
        if (template == null) throw new RuntimeException("No RocketMQTemplate found for key: " + key);
        Message<String> message = MessageBuilder.withPayload(msg)
                .setHeader("KEYS", msgKey)
                .build();
        template.send(topic, message);
    }

    public static void sendMessageWithTag(String key, String topic, String tag, String msg) {
        RocketMQTemplate template = staticTemplateMap.get(key);
        if (template == null) throw new RuntimeException("No RocketMQTemplate found for key: " + key);
        template.convertAndSend(topic + ":" + tag, msg);
    }

    public static void sendMessage(String key, String topic, String tag, String msgKey, String msg) {
        RocketMQTemplate template = staticTemplateMap.get(key);
        if (template == null) throw new RuntimeException("No RocketMQTemplate found for key: " + key);
        Message<String> message = MessageBuilder.withPayload(msg)
                .setHeader("KEYS", msgKey)
                .build();
        template.send(topic + ":" + tag, message);
    }

    public static void sendTransactionMessage(String key, String topic, String transId, String msg) {
        RocketMQTemplate template = staticTemplateMap.get(key);
        if (template == null) throw new RuntimeException("No RocketMQTemplate found for key: " + key);
        Message<String> message = MessageBuilder.withPayload(msg)
                .setHeader("TRANSACTION_ID", transId)
                .build();
        template.sendMessageInTransaction(topic, message, transId);
    }
}
