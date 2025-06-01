package com.haydenshui.stock.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

@Aspect
@Component
public class LocalTccAspect {

    @Pointcut("@annotation(com.haydenshui.stock.annotation.LocalTcc)")
    public void noTransactionalMethods() {
    }

    @Before("noTransactionalMethods()")
    public void checkNoTransactional(Method method) {
        if (method.isAnnotationPresent(Transactional.class)) {
            throw new IllegalStateException("Method: " + method.getName() + " marked with @LocalTcc cannot be annotated with @Transactional");
        }
    }
}
