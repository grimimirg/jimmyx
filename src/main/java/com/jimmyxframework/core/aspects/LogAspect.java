package com.jimmyxframework.core.aspects;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

public class LogAspect {

    /**
     *
     */
    @Pointcut("execution(* *(..)) && @annotation(com.jimmyxframework.core.annotation.loggin.Log)")
    public void logPointcut() {
    }

    //*******************************************************************************************

    /**
     * @param joinPoint
     */
    @Before("logPointcut()")
    public void log(JoinPoint joinPoint) {
        try {
            this.log(joinPoint.getArgs(), joinPoint.getTarget().getClass(),
                    joinPoint.getSignature().getName());
        } catch (Exception e) {
            // empty at the moment
        }
    }

    //*******************************************************************************************

    /**
     * @param args
     * @param className
     * @param methodName
     */
    public void log(Object[] args, Class<? extends Object> className,
                    String methodName) {
        if (className != null) {
            Logger logger = LogManager.getLogger(className);
            for (Object arg : args) {
                if (arg != null) {
                    logger.info(methodName + " - " + arg.toString());
                }
            }
        }
    }

}
