package com.ym.learn.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/8 16:38
 * @Desc: 测试模块启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
