package com.jimmyxframework.core.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.vertx.core.http.HttpMethod;

/**
 * @author Andrea_Grimandi
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface RequestMapping {

    /**
     * @return
     */
    HttpMethod method() default HttpMethod.GET;

    /**
     * @return
     */
    String path();

    /**
     * @return
     */
    String headers() default "";
}
