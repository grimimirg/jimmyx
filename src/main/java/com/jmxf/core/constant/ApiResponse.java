package com.jmxf.core.constant;

import org.apache.commons.httpclient.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ApiResponse {

	public static final int OK_CODE = 200;
	public static final int INTERNAL_SERVER_ERROR_CODE = 1;
	public static final int MISSING_PARAMETER_CODE = 2;
	public static final int MISSING_CLIENT_ID_HEADER_CODE = 10;
	public static final int MISSING_JWT_HEADER_CODE = 11;
	public static final int INVALID_JWT_TOKEN_CODE = 12;
	public static final int EXPIRED_JWT_TOKEN_CODE = 13;
	public static final int NOT_FOUND_CODE = 20;
	public static final int USER_ALREADY_EXISTS_CODE = 21;
	public static final int WRONG_PASSWORD_CODE = 22;
	public static final int UNAUTHORIZED_CODE = 23;
	public static final int INVALID_INPUT_VALUE_CODE = 55;

	public static final String OK = "OK";
	public static final String INTERNAL_SERVER_ERROR = "Internal server error";
	public static final String MISSING_PARAMETER = "Required parameter missing";
	public static final String MISSING_CLIENT_ID_HEADER = "The request doesn't contain a correct header with the client id";
	public static final String MISSING_JWT_HEADER = "JWT token missing in the request";
	public static final String INVALID_JWT_TOKEN = "The request contains an invalid JWT token";
	public static final String EXPIRED_JWT_TOKEN = "The request contains an expired JWT token";
	public static final String NOT_FOUND = "The content requested can't be found";
	public static final String UNAUTHORIZED = "User unauthorized";
	public static final String INVALID_INPUT_VALUE = "Invalid input value";

	public ApiResponse() {
	}

	public ApiResponse(int code) {
		this.code = code;
		switch (code) {

		case OK_CODE:
			this.message = OK;
			this.status = HttpStatus.SC_OK;
			break;
		case INTERNAL_SERVER_ERROR_CODE:
			this.message = INTERNAL_SERVER_ERROR;
			this.status = HttpStatus.SC_INTERNAL_SERVER_ERROR;
			break;
		case MISSING_PARAMETER_CODE:
			this.message = MISSING_PARAMETER;
			this.status = HttpStatus.SC_BAD_REQUEST;
			break;
		case MISSING_CLIENT_ID_HEADER_CODE:
			this.message = MISSING_CLIENT_ID_HEADER;
			this.status = HttpStatus.SC_UNAUTHORIZED;
			break;
		case MISSING_JWT_HEADER_CODE:
			this.message = MISSING_JWT_HEADER;
			this.status = HttpStatus.SC_UNAUTHORIZED;
			break;
		case INVALID_JWT_TOKEN_CODE:
			this.message = INVALID_JWT_TOKEN;
			this.status = HttpStatus.SC_UNAUTHORIZED;
			break;
		case EXPIRED_JWT_TOKEN_CODE:
			this.message = EXPIRED_JWT_TOKEN;
			this.status = HttpStatus.SC_UNAUTHORIZED;
			break;
		case NOT_FOUND_CODE:
			this.message = NOT_FOUND;
			this.status = HttpStatus.SC_NOT_FOUND;
			break;
		case UNAUTHORIZED_CODE:
			this.message = UNAUTHORIZED;
			this.status = HttpStatus.SC_UNAUTHORIZED;
			break;
		case INVALID_INPUT_VALUE_CODE:
			this.message = INVALID_INPUT_VALUE;
			this.status = HttpStatus.SC_BAD_REQUEST;
			break;
		}
	}

	public ApiResponse(String message, int code, int status) {
		this.message = message;
		this.code = code;
		this.status = status;
	}

	private int code;

	private String message;

	@JsonIgnore
	private int status;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toString() {
		return new StringBuilder().append(getCode()).append(": ").append(getMessage()).toString();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
