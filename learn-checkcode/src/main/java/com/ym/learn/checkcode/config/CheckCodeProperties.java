package com.ym.learn.checkcode.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/21 19:44
 * @Desc:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "checkcode")
public class CheckCodeProperties {
    /**
     * 验证码长度
     */
    private Integer length;
    /**
     * key前缀
     */
    private String prefixKey;
    /**
     * 缓存过期时间
     */
    private Integer expireTime;

}
