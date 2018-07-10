package com.jmxf.core.annotation.logging;

import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * @author Andrea_Grimandi
 */
@Target({METHOD, TYPE})
public @interface Log {
}
