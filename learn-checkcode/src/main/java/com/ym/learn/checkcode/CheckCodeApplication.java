package com.ym.learn.checkcode;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/21 16:19
 * @Desc: 验证码入口服务类
 */
@SpringBootApplication
public class CheckCodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(CheckCodeApplication.class,args);
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> configure(@Value("${spring.application.name}") String applicationName){
        return (registry -> {
            registry.config().commonTags("application",applicationName);
        });
    }
}
