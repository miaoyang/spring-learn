package com.ym.learn.checkcode.service;

import com.ym.learn.checkcode.domain.CodeVo;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/21 17:32
 * @Desc:
 */
public interface CheckCodeService {

    /**
     * 校验验证码
     * @param key
     * @param code
     * @return
     */
    boolean verifyCode(String key, String code);

    /**
     * 生成验证码
     * @param length
     * @return
     */
    CodeVo generateCode(Integer length, String prefixKey,Integer expireTime);
}
