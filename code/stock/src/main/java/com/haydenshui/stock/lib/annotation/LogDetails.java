package com.haydenshui.stock.lib.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogDetails {
    String value() default "";
}