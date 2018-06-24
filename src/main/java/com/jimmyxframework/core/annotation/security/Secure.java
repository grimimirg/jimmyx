package com.jimmyxframework.core.annotation.security;

import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

/**
 * @author Andrea_Grimandi
 */
@Target(PARAMETER)
public @interface Secure {
}
