package com.haydenshui.stock.config;

import lombok.Data;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rocketmq-config")
public class RocketMQConfigProperties {

    private Map<String, String> producers;
    private Map<String, String> consumers;
    private Map<String, String> topics;
    private Map<String, String> tags;

}
