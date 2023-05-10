package com.ym.learn.api.dubbo;

import com.ym.learn.api.component.LoginUserHolder;
import com.ym.learn.core.api.R;
import com.ym.learn.rpc.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/5 16:14
 * @Desc: rpc UserService实现类
 */
@DubboService
public class UserServiceImpl implements UserService {
    @Autowired
    private LoginUserHolder loginUserHolder;

    @Override
    public R getUser(String name) {
        return R.ok(loginUserHolder.getCurrentUser());
    }
}
