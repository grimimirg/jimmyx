package com.jimmyxframework.core.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 *
 */
public class SecurityAspect {
    /**
     *
     */
    @Pointcut("execution(* *(..)) && @annotation(com.jimmyxframework.core.annotation.security.Secure)")
    public void securePointcut() {

    }

    @Before("securePointcut()")
    public void log(JoinPoint joinPoint) {
// do sth
    }
}
