package com.haydenshui.stock.lib.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdGeneratorFactory {

    private final SnowflakeIdGenerator snowflakeIdGenerator;

    public SnowflakeIdGeneratorFactory(@Value("${snowflake.workerId}") long workerId) {
        this.snowflakeIdGenerator = new SnowflakeIdGenerator(workerId);
    }

    public SnowflakeIdGenerator getSnowflakeIdGenerator() {
        return snowflakeIdGenerator;
    }
}
