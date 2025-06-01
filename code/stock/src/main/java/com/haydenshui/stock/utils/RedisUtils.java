package com.haydenshui.stock.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    private static StringRedisTemplate staticRedisTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate; 

    @PostConstruct
    public void init() {
        staticRedisTemplate = this.redisTemplate; 
    }

    public static void set(String key, String value) {
        staticRedisTemplate.opsForValue().set(key, value);
    }

    public static boolean setIfAbsent(String key, String value, long timeout, TimeUnit unit) {
        Boolean success = staticRedisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
        return Boolean.TRUE.equals(success);
    }
    
    public static String get(String key) {
        return staticRedisTemplate.opsForValue().get(key);
    }

    public static void delete(String key) {
        staticRedisTemplate.delete(key);
    }

    public static boolean hasKey(String key) {
        return Boolean.TRUE.equals(staticRedisTemplate.hasKey(key));
    }

    public static void expire(String key, long timeout, TimeUnit unit) {
        staticRedisTemplate.expire(key, timeout, unit);
    }

    public static void hSet(String key, String field, String value) {
        staticRedisTemplate.opsForHash().put(key, field, value);
    }

    public static String hGet(String key, String field) {
        Object value = staticRedisTemplate.opsForHash().get(key, field);
        return value != null ? value.toString() : null;
    }
    

    public static Map<Object, Object> hGetAll(String key) {
        return staticRedisTemplate.opsForHash().entries(key);
    }
}
