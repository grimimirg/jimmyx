package com.test.app.controller;

import com.jimmyxframework.core.annotation.ApiDefaultResponse;
import com.jimmyxframework.core.annotation.Controller;
import com.jimmyxframework.core.annotation.ParamQuery;
import com.jimmyxframework.core.annotation.RequestMapping;
import com.jimmyxframework.core.annotation.SerializeJson;
import com.jimmyxframework.core.annotation.security.Secure;
import io.vertx.core.http.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;

/**
 * @author Andrea_Grimandi
 */
@Controller(path = "/users")
public class UserController {

    @RequestMapping(path = "/test", method = HttpMethod.GET)
    @ApiDefaultResponse(status = HttpStatus.SC_OK)
    public @SerializeJson
    void get(@ParamQuery(value = "p1") @Secure String p1, @ParamQuery(value = "p2") @Secure Integer p2,
                @ParamQuery(value = "p3") String p3) {
        System.out.println(p1 + " - " + p2 + " - " + p3);
    }

}
