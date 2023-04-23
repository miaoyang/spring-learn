package com.ym.learn.checkcode.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/21 17:36
 * @Desc: 返回给客户端的Code
 */
@Data
@ToString
@Builder
public class CodeVo {
    /**
     * 用于验证的key
     */
    private String key;
    /**
     * 校验码
     */
    private String aliasing;
}
