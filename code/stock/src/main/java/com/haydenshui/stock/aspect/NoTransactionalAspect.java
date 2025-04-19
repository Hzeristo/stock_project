package com.haydenshui.stock.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

@Aspect
@Component
public class NoTransactionalAspect {

    @Pointcut("@annotation(com.haydenshui.stock.annotation.NoTransactional)")
    public void noTransactionalMethods() {
    }

    @Before("noTransactionalMethods()")
    public void checkNoTransactional(Method method) {
        if (method.isAnnotationPresent(Transactional.class)) {
            throw new IllegalStateException("Method: " + method.getName() + " marked with @NoTransactional cannot be annotated with @Transactional");
        }
    }
}
