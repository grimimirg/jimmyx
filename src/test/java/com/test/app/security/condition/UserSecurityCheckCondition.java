package com.test.app.security.condition;

import com.jmxf.core.security.condition.ConditionSecurityCheck;

public class UserSecurityCheckCondition implements ConditionSecurityCheck {

	@Override
	public boolean isValidationSatisfied(Object object) {
		
		return false;
	}

}
