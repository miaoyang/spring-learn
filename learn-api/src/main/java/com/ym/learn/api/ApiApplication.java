package com.ym.learn.api;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Yangmiao
 * @Date: 2023/3/21 16:18
 * @Desc:
 */
@SpringBootApplication
@EnableDubbo
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class,args);
    }
}
