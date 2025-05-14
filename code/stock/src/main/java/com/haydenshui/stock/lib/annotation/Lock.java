package com.haydenshui.stock.lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)  // 目标是方法
@Retention(RetentionPolicy.RUNTIME)  // 保留在运行时
public @interface Lock {
    String lockKey();  // 锁的 key
    long waitTime() default 10L;  // 最大等待时间，单位秒
    long leaseTime() default 30L;  // 锁的自动释放时间，单位秒
}
