package com.jimmyxframework.core.annotation;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Target;

/**
 * 
 * @author Andrea_Grimandi
 *
 */
@Target(METHOD)
public @interface ApiResponse {

	/**
	 * 
	 * @return
	 */
	int status() default 200;
}
