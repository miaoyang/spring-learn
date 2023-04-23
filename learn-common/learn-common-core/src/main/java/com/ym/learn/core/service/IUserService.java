package com.ym.learn.core.service;

import com.ym.learn.core.entity.LoginUser;
import com.ym.learn.core.api.R;

/**
 * @Author: Yangmiao
 * @Date: 2023/3/21 11:06
 * @Desc: 获取账号的接口
 */
public interface IUserService {
    /**
     * 获取登录用户信息
     * @param name
     * @return
     */
    R<LoginUser> getUserByName(String name);
}
