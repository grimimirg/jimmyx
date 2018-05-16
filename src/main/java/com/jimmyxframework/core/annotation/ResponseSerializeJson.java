package com.jimmyxframework.core.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * @author Andrea_Grimandi
 *
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface ResponseSerializeJson {

}
