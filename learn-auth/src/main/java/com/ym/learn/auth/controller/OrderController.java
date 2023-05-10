package com.ym.learn.auth.controller;

import com.ym.learn.core.api.R;
import com.ym.learn.rpc.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yangmiao
 * @Date: 2023/3/21 16:57
 * @Desc:
 */
@RestController("/order")
public class OrderController {
    @DubboReference
    private UserService userService;

    @RequestMapping("/getUser")
    public R getUser(){
        return userService.getUser("hahaha");
    }
}
