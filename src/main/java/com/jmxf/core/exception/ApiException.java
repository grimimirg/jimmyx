package com.jmxf.core.exception;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 5963884972499990386L;

	public ApiException(int msg) {
		super(String.valueOf(msg));
	}

}
