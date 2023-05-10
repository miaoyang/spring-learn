package com.ym.learn.rocketmq.config;

import com.ym.learn.rocketmq.service.MQProducer;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/6 16:51
 * @Desc: RocketMQ配置类
 */
@Data
@ConfigurationProperties(prefix = "rocketmq")
@Configuration
public class RocketMqProperties {

    private String nameServer;

    private String producerGroup;

    private int sendMessageTimeout;

    private int retryTimesWhenSendFailed;

    private int retryTimesWhenSendAsyncFailed;

    @Bean
    public MQProducer mqProducer(){
        return new MQProducer();
    }
}
