package com.ym.learn.checkcode.config;

import com.ym.learn.swagger.config.BaseKnife4jConfig;
import com.ym.learn.swagger.domain.Knife4jProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/21 22:07
 * @Desc:
 */
@Configuration
@EnableOpenApi
public class Knife4jConfig extends BaseKnife4jConfig {
    @Override
    public Knife4jProperties knife4jProperties() {
        return Knife4jProperties.builder()
                .apiBasePackage("com.ym.learn.checkcode")
                .title("验证码服务接口文档")
                .description("验证码服务接口文档")
                .contactName("ym")
                .version("1.0")
                .enableSecurity(false)
                .build();
    }
}
