package com.jmxf.core.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmxf.core.constant.ApiResponse;
import com.jmxf.core.constant.Constants;

import io.vavr.control.Try;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 5963884972499990386L;

	public ApiException(int msg) {
		super(Try.of(() -> new ObjectMapper().writeValueAsString(new ApiResponse(msg)))
				.getOrElse(Constants.ERRORS.GENERIC.get()));
	}

}
