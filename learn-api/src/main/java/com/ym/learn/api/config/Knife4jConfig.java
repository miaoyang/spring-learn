package com.ym.learn.api.config;

import com.ym.learn.swagger.config.BaseKnife4jConfig;
import com.ym.learn.swagger.domain.Knife4jProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/18 10:27
 * @Desc: knife4j配置
 */
@Configuration
@EnableOpenApi
public class Knife4jConfig extends BaseKnife4jConfig {
    @Override
    public Knife4jProperties knife4jProperties() {
        return Knife4jProperties.builder()
                .apiBasePackage("com.ym.learn.api")
                .title("Api接口文档")
                .description("Api接口文档")
                .contactName("ym")
                .version("1.0")
                .enableSecurity(false)
                .build();
    }
}
