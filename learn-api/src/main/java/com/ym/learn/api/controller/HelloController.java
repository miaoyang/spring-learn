package com.ym.learn.api.controller;

import com.ym.learn.api.component.LoginUserHolder;
import com.ym.learn.core.entity.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/9 22:45
 * @Desc:
 */
@RestController
@Api(tags = "hello测试接口")
public class HelloController {

    @ApiOperation(value = "hello测试")
    @GetMapping("/hello")
    public String hello() {
        return "Hello World.";
    }

    @Autowired
    private LoginUserHolder loginUserHolder;

    @GetMapping("/currentUser")
    public UserDTO currentUser() {
        return loginUserHolder.getCurrentUser();
    }
}
