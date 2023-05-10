package com.ym.learn.test.rocketmq.consumer;

import com.ym.learn.rocketmq.constant.RocketConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/8 20:09
 * @Desc: 消息消费处理
 */
@Slf4j
@RocketMQMessageListener(topic = RocketConstant.TOPIC_DEFAULT, consumerGroup = "Con_Group_Two")
public class MQConsumerMsgTwo implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        log.info("MQConsumerMsgTwo: 消费消息：{}", s);
    }
}
