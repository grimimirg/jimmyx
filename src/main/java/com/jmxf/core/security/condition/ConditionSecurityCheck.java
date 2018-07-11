package com.jmxf.core.security.condition;

public interface ConditionSecurityCheck {

	/**
	 * perform a custom object validation based on given criteria. Criteria must be
	 * explicited overriding this function.
	 */
	public boolean isValidationSatisfied(Object object);

}
