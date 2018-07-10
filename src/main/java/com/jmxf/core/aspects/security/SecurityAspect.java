package com.jmxf.core.aspects.security;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

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
		GENERIC, OBJECT
	}

	/**
	 * Eseguito come controllo di sicurezza sulla conformità dle contenuto dei
	 * aprametri per evitare sql injection/altro
	 */
	@Pointcut("execution(* *(..)) && @annotation(com.jmxf.core.annotation.security.Secure)")
	public void genericSecurityCheck() {
	}

	// -----------------------------------------------------------------------------------------------------

	/**
	 * qualsiasi metodo di qualsiasi classe che abbia come annotation @Secure passa
	 * da questa funzione la quale procede con l'analizzare tutti i parametri
	 * presenti.
	 * 
	 * @param joinPoint
	 */
	@Before("genericSecurityCheck()")
	public void applyGenericSecurityCheck(JoinPoint joinPoint) {
		// a partire dalla firma del metono vengono prese tutte le annotation presenti
		// sui singoli parametri
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();

		// i valori dei vari parametri
		Object[] args = joinPoint.getArgs();

		// il controllo viene effettuato su ogni parametro. la funzione check
		// discriminerà quale tipo di controllo effettuare
		for (int i = 0; i < args.length; i++) {
			if (args[i] != null) {
				this.check(this.getCheckType(parameterAnnotations[i]), args[i]);
			}
		}

	}

	/**
	 * in base al tipo di controllo esplicitato sui singoli parametri, viene
	 * effettuato il controllo opportuno
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
		}
		return null;
	}

	/**
	 * all'interno dei vari case è possibile gestire diversamente l'eccezione da
	 * ritornare
	 * 
	 * @param checkType
	 * @param arg
	 */
	private void check(SecurityCheckType checkType, Object arg) {
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
				// TODO gestire in modo non bloccate l'eccezione
			}
			break;
		default:
			break;
		}
	}

}
