package com.haydenshui.stock.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Bean
    RedissonClient redissonClient() {
        Config config = new Config();
        // 单机模式配置
        config.useSingleServer()
              .setAddress("redis://localhost:6379")
              .setPassword(null)
              .setDatabase(0)
              .setTimeout(3000)
              .setConnectionMinimumIdleSize(5)
              .setConnectionPoolSize(10);
        return Redisson.create(config);
    }
}
