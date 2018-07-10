package com.jmxf.core.security;

import java.lang.reflect.Method;

public class Security {

	/**
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean genericSecurityCheck(Object arg) {
		String tmp = null;

		if (arg instanceof String) {
			tmp = (String) arg;
			tmp = tmp.toLowerCase();
		}

		return tmp == null ? true
				: !((tmp.substring(0, 1).equals("'") || tmp.substring(tmp.length() - 1, tmp.length()).equals("'"))
						|| (tmp.contains("+") || tmp.contains(" + ") || tmp.contains("+ "))
						|| (tmp.contains(" and ") || tmp.contains(" and") || tmp.contains("and "))
						|| (tmp.contains(" or ") || tmp.contains(" or") || tmp.contains("or "))
						|| (tmp.contains(" union ") || tmp.contains(" union") || tmp.contains("union ")));
	}

	/**
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static boolean genericSecurityCheckObject(Object object) throws Exception {
		Method[] methods = object.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().toLowerCase().substring(0, 3).equals("get")
					&& !method.getName().toLowerCase().equals("getclass")) {
				return genericSecurityCheck(method.invoke(object, null));
			}
		}
		return true;
	}

}
