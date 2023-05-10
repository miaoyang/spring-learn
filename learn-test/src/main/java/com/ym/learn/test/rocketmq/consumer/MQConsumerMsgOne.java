package com.ym.learn.test.rocketmq.consumer;

import com.ym.learn.rocketmq.constant.RocketConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/8 17:07
 * @Desc: 消费消息
 */
@Service
@Slf4j
@RocketMQMessageListener(consumerGroup = "Con_Group_One", topic = RocketConstant.TOPIC_DEFAULT)
public class MQConsumerMsgOne implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        log.info("MQConsumerMsgOne: 接收到消息：{}", s);
    }
}
