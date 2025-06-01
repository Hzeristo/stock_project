package com.haydenshui.stock.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.haydenshui.stock.constants.ErrorCode;
import com.haydenshui.stock.lib.exception.RepositoryException;

@Aspect
@Component
public class RepoGuardAspect {

    @Pointcut("@annotation(com.haydenshui.stock.annotation.RepoGuard)")
    public void repoGuardMethods() {}

    @Around("repoGuardMethods()")
    public Object repoGuard(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            throw new RepositoryException(null, e, ErrorCode.INNER_DATABASE_ERROR.getCode());
        }
    }

    
}
