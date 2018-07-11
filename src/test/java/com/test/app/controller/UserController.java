package com.test.app.controller;

import org.apache.commons.httpclient.HttpStatus;

import com.jmxf.core.annotation.ApiDefaultResponse;
import com.jmxf.core.annotation.Controller;
import com.jmxf.core.annotation.ParamBody;
import com.jmxf.core.annotation.ParamQuery;
import com.jmxf.core.annotation.RequestMapping;
import com.jmxf.core.annotation.SerializeJson;
import com.jmxf.core.annotation.security.Secure;
import com.jmxf.core.annotation.security.SecureGenericCheck;
import com.test.app.model.User;

import io.vertx.core.http.HttpMethod;

/**
 * @author Andrea_Grimandi
 */
@Controller(path = "/users")
public class UserController {

	@RequestMapping(path = "/test", method = HttpMethod.GET)
	@ApiDefaultResponse(status = HttpStatus.SC_OK)
	@Secure
	public @SerializeJson void get(@ParamQuery(value = "p1") @SecureGenericCheck String p1,
			@ParamQuery(value = "p2") Integer p2, @ParamQuery(value = "p3") String p3, @ParamBody User user) {
//		throw new ApiException(ApiResponse.UNAUTHORIZED_CODE);
//		System.out.println(p1 + " - " + p2 + " - " + p3);
	}

}
