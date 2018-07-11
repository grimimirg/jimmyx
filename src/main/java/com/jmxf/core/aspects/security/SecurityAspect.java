package com.jmxf.core.aspects.security;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.jmxf.core.annotation.security.SecureConditionCheck;
import com.jmxf.core.annotation.security.SecureGenericCheck;
import com.jmxf.core.annotation.security.SecureGenericCheckObject;
import com.jmxf.core.constant.ApiResponse;
import com.jmxf.core.exception.ApiException;
import com.jmxf.core.security.Security;

/**
 *
 */
@Aspect
public class SecurityAspect {

	private enum SecurityCheckType {
		GENERIC, OBJECT, CONDITION
	}

	/**
	 */
	@Pointcut("execution(* *(..)) && @annotation(com.jmxf.core.annotation.security.Secure)")
	public void genericSecurityCheck() {
	}

	// -----------------------------------------------------------------------------------------------------

	/**
	 * 
	 * @param joinPoint
	 */
	@Before("genericSecurityCheck()")
	public void applyGenericSecurityCheck(JoinPoint joinPoint) {
		// get all parameters annotations
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();

		// all arguments values
		Object[] args = joinPoint.getArgs();

		// checks will be performed for each annotated arguments
		for (int i = 0; i < args.length; i++) {
			if (args[i] != null) {
				this.check(this.getCheckType(parameterAnnotations[i]), parameterAnnotations[i], args[i]);
			}
		}

	}

	/**
	 * delegates the suitable check to perform
	 * 
	 * @param parameterAnnotations
	 * @return
	 */
	private SecurityCheckType getCheckType(Annotation[] parameterAnnotations) {
		for (Annotation annotation : parameterAnnotations) {
			if (annotation.annotationType().equals(SecureGenericCheck.class)) {
				return SecurityCheckType.GENERIC;
			}
			if (annotation.annotationType().equals(SecureGenericCheckObject.class)) {
				return SecurityCheckType.OBJECT;
			}
			if (annotation.annotationType().equals(SecureConditionCheck.class)) {
				return SecurityCheckType.CONDITION;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param checkType
	 * @param arg
	 */
	private void check(SecurityCheckType checkType, Annotation[] annotations, Object arg) {
		switch (checkType) {
		case GENERIC:
			boolean genericSecurityCheck = Security.genericSecurityCheck(arg);
			if (!genericSecurityCheck) {
				throw new ApiException(ApiResponse.INVALID_INPUT_VALUE_CODE);
			}
			break;
		case OBJECT:
			try {
				boolean genericSecurityCheckObject = Security.genericSecurityCheckObject(arg);
				if (!genericSecurityCheckObject) {
					throw new ApiException(ApiResponse.INVALID_INPUT_VALUE_CODE);
				}
			} catch (Exception e) {
				// TODO empty since now
			}
			break;
		case CONDITION:
			try {
				boolean specificConditionCheck = Security.specificConditionCheck(annotations, arg);
				if (!specificConditionCheck) {
					throw new ApiException(ApiResponse.INVALID_INPUT_VALUE_CODE);
				}
			} catch (Exception e) {
				throw new ApiException(ApiResponse.INVALID_INPUT_VALUE_CODE);
			}
			break;
		default:
			break;
		}
	}

}
