package com.haydenshui.stock.utils;

import jakarta.annotation.PostConstruct;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedissonUtils {

    private static RedissonClient staticRedissonClient;

    @Autowired
    private RedissonClient redissonClient;

    @PostConstruct
    public void init() {
        staticRedissonClient = this.redissonClient;
    }

    public static RLock lock(String lockKey) {
        RLock lock = staticRedissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    public static RLock lock(String lockKey, long timeout, TimeUnit unit) {
        RLock lock = staticRedissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    public static boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit) {
        RLock lock = staticRedissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public static void unlock(String lockKey) {
        RLock lock = staticRedissonClient.getLock(lockKey);
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    public static void unlock(RLock lock) {
        if (lock != null && lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}
