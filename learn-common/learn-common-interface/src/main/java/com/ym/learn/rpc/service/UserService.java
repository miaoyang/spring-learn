package com.ym.learn.rpc.service;

import com.ym.learn.core.api.R;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/5 16:08
 * @Desc: 远程调用UserService
 */
public interface UserService {
    /**
     * 获取用户名
     * @param name
     * @return
     */
    R getUser(String name);
}
