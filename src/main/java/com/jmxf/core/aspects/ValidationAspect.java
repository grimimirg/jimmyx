package com.jmxf.core.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.jmxf.core.Validation;

/**
 *
 */
@Aspect
public class ValidationAspect {

	/**
	 * 
	 */
	@Pointcut("execution(* *(..)) && @annotation(com.jmxf.core.annotation.RequestMapping)")
	public void validationPointcut() {
	}

	/**
	 * 
	 * @param joinPoint
	 */
	@Before("validationPointcut()")
	public void validation(JoinPoint joinPoint) {
		Validation.validateController((MethodSignature) joinPoint.getSignature(), joinPoint.getArgs());
	}

}
