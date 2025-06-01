package com.haydenshui.stock.lib.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RepositoryGuard {
}