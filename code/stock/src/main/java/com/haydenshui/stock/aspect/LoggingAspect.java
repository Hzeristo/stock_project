package com.haydenshui.stock.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.haydenshui.stock.securities.security.SecuritySecuritiesAccount;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger controlLogger = LogManager.getLogger("ControllerLogger");

    private static final Logger serviceLogger = LogManager.getLogger("ServiceLogger");
    @Pointcut("@annotation(com.haydenshui.stock.annotation.LogTime)")
    public void logTimeMethods() {}

    @Pointcut("@annotation(com.haydenshui.stock.annotation.LogDetails)")
    public void logDetailsMethods() {}

    @Pointcut("@annotation(com.haydenshui.stock.annotation.ServiceLog)")
    public void serviceLogMethods() {}

    @Around("logTimeMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        controlLogger.info("[Method: {}] executed in {} ms", joinPoint.getSignature().getName(), end - start);
        return result;
    }

    @Around("logDetailsMethods()")
    public Object logMethodDetails(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = principal instanceof SecuritySecuritiesAccount ? ((SecuritySecuritiesAccount) principal).getId() : null;

        String methodName = joinPoint.getSignature().getName();
        String httpMethod = request.getMethod();
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();

        Object[] args = joinPoint.getArgs();
        String argsStr = Arrays.stream(args)
                .filter(arg -> !(arg instanceof HttpServletRequest))
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        controlLogger.info(
            "[UserID: {}] [IP: {}] [Request: {} {}] [Method: {}] [Args: {}] [Duration: {} ms]",
            userId, ip, httpMethod, uri, methodName, argsStr, (end - start)
        );

        return result;
    }

    @AfterThrowing(pointcut = "logDetailsMethods()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = principal instanceof SecuritySecuritiesAccount ? ((SecuritySecuritiesAccount) principal).getId() : null;

        String methodName = joinPoint.getSignature().getName();
        String httpMethod = request.getMethod();
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();

        Object[] args = joinPoint.getArgs();
        String argsStr = Arrays.stream(args)
                .filter(arg -> !(arg instanceof HttpServletRequest))
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        
        String logMessage = String.format(
            "[UserID: %s] [IP: %s] [Request: %s %s] [Method: %s] [Args: %s] [Exception: %s]",
            userId, ip, httpMethod, uri, methodName, argsStr, ex.getMessage()
        );

        controlLogger.error(logMessage, ex);

    }

    @Around("serviceLogMethods()")
        public Object logServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable {
            String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        long startTime = System.currentTimeMillis();

        try {
            serviceLogger.info(" [Service] {}.{} called with args: {}", className, methodName, Arrays.toString(args));

            Object result = joinPoint.proceed();

            long endTime = System.currentTimeMillis();
            serviceLogger.info(" [Service] {}.{} finished in {} ms", className, methodName, (endTime - startTime));

            return result;
        } catch (Throwable e) {
            long errorTime = System.currentTimeMillis();
            serviceLogger.warn("[Service] {}.{} threw exception after {} ms: {}", className, methodName, (errorTime - startTime), e.getMessage(), e);
            throw e;
        }
    }

}
