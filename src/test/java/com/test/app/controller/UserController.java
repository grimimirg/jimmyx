package com.test.app.controller;

import com.jimmyxframework.core.annotation.ApiResponse;
import com.jimmyxframework.core.annotation.Controller;
import com.jimmyxframework.core.annotation.ParamQuery;
import com.jimmyxframework.core.annotation.RequestMapping;
import com.jimmyxframework.core.annotation.ResponseSerializeJson;

/**
 * @author Andrea_Grimandi
 */
@Controller(path = "/users")
public class UserController {

    @RequestMapping(path = "/test")
    @ApiResponse(status = 200)
    public @ResponseSerializeJson
    void getAll(@ParamQuery(value = "p1") String p1, @ParamQuery(value = "p2") Integer p2,
                @ParamQuery(value = "p3") String p3) {
        System.out.println(p1 + " - " + p2 + " - " + p3);
    }

}
