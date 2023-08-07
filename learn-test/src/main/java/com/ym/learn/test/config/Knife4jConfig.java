package com.ym.learn.test.config;

import com.ym.learn.swagger.config.BaseKnife4jConfig;
import com.ym.learn.swagger.domain.Knife4jProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/8 16:44
 * @Desc: knife4j配置类
 */
@Configuration
@EnableOpenApi
public class Knife4jConfig extends BaseKnife4jConfig {

    @Override
    public Knife4jProperties knife4jProperties() {
        return Knife4jProperties.builder()
                .apiBasePackage("com.ym.learn.test")
                .description("learn-test模块knife4j测试接口")
                .contactName("ym")
                .title("learn-test模块knife4j测试接口")
                .version("1.0.0")
                .enableSecurity(false)
                .build();
    }
}
