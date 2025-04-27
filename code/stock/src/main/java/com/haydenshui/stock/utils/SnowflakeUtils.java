package com.haydenshui.stock.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.haydenshui.stock.lib.util.SnowflakeIdGenerator;

import jakarta.annotation.PostConstruct;

@Component
public class SnowflakeUtils {

    private static SnowflakeIdGenerator staticGenerator;

    private final long workerId;

    public SnowflakeUtils(@Value("${snowflake.workerId}") long workerId) {
        this.workerId = workerId;
    }

    @PostConstruct
    public void init() {
        staticGenerator = new SnowflakeIdGenerator(workerId);
    }

    public static long nextId() {
        return staticGenerator.nextId();
    }

    public static String nextIdStr() {
        return String.valueOf(staticGenerator.nextId());
    }
}
