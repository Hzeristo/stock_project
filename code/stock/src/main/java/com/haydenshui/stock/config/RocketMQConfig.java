package com.haydenshui.stock.config;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMQConfig {

    @Bean
    RocketMQTemplate rocketMQTemplate() {
        return new RocketMQTemplate();
    }
    
}