package com.haydenshui.stock.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMQConfig {

    @Autowired
    private RocketMQProperties rocketMQProperties;

    @Autowired
    private RocketMQConfigProperties rocketMQConfigProperties; 

    @Bean
    RocketMQTemplate rocketMQTemplate() {
        return new RocketMQTemplate();
    }

    @Bean
    Map<String, RocketMQTemplate> rocketMQTemplates() {
        Map<String, RocketMQTemplate> templates = new HashMap<>();

        rocketMQConfigProperties.getProducers().forEach((key, group) -> {
            try {
                DefaultMQProducer producer = new DefaultMQProducer(group);
                producer.setNamesrvAddr(rocketMQProperties.getNameServer());
                producer.setSendMsgTimeout(rocketMQProperties.getProducer().getSendMessageTimeout());
                producer.start();
    
                RocketMQTemplate rocketMQTemplate = new RocketMQTemplate();
                rocketMQTemplate.setProducer(producer);
                templates.put(key, rocketMQTemplate);
            } catch (MQClientException e) {
                throw new RuntimeException("启动 producer 失败: " + group, e);
            }
        });
        return templates;
    }
    
}