package com.haydenshui.stock.lib.util;

public class SnowflakeIdGenerator {

    // 自定义起始时间戳（2020-01-01 00:00:00）
    private static final long START_TIMESTAMP = 1609459200000L;

    private static final long WORKER_ID_BITS = 5L;
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    private static final long SEQUENCE_BITS = 12L;
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private static final long WORKER_ID_LEFT_SHIFT = SEQUENCE_BITS;

    private long sequence = 0L;
    private long lastTimestamp = -1L;

    private final long workerId;

    public SnowflakeIdGenerator(long workerId) {
        if (workerId < 0 || workerId > MAX_WORKER_ID) {
            throw new IllegalArgumentException(
                    String.format("Worker ID can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        this.workerId = workerId;
    }

    private long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    private long waitForNextMillis(long lastTimestamp) {
        long timestamp = getCurrentTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentTimestamp();
        }
        return timestamp;
    }

    public synchronized long generateId() {
        long timestamp = getCurrentTimestamp();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id.");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                timestamp = waitForNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return (timestamp - START_TIMESTAMP) << TIMESTAMP_LEFT_SHIFT
                | (workerId << WORKER_ID_LEFT_SHIFT)
                | sequence;
    }

    public long nextId() {
        return generateId();
    }

    public String nextIdStr() {
        return String.valueOf(generateId());
    }

    public long getWorkerId() {
        return workerId;
    }
}
