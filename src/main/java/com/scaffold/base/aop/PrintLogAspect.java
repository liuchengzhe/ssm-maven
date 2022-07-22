package com.scaffold.base.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class PrintLogAspect {
    @Around(value = "@annotation(com.scaffold.base.aop.PrintLog)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            Method method = signature.getMethod();
            PrintLog printLog = method.getAnnotation(PrintLog.class);

            log.info("【{}#{}】打印日志：{}", proceedingJoinPoint.getTarget().getClass().getName(), method.getName(), printLog.log());

            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("日志打印异常:", throwable);
        }
        return null;
    }
}
