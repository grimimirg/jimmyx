package com.jmxf.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.reflect.MethodSignature;

import com.jmxf.core.annotation.ParamBody;
import com.jmxf.core.annotation.ParamPath;
import com.jmxf.core.annotation.ParamQuery;

public class Validation {

	/**
	 * 
	 * @param methodSignature
	 * @param args
	 * @return
	 */
	public static boolean validateController(MethodSignature methodSignature, Object[] args) {
		Method method = methodSignature.getMethod();
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();

		for (int i = 0; i < args.length; i++) {
			if (args[i] != null) {
				if (!performCheck(parameterAnnotations[i], args[i])) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 
	 * @param parameterAnnotations
	 * @param arg
	 * @return
	 */
	private static boolean performCheck(Annotation[] parameterAnnotations, Object arg) {
		for (Annotation annotation : parameterAnnotations) {
			if (annotation.annotationType().equals(ParamBody.class)
					|| annotation.annotationType().equals(ParamPath.class)) {
				// must be always present
				return arg != null;
			}
			if (annotation.annotationType().equals(ParamQuery.class)) {
				ParamQuery paramQuery = (ParamQuery) annotation;
				// if required was explicited to true, parameter should be present
				return paramQuery.required() && arg != null;
			}
		}

		return true;
	}

}
