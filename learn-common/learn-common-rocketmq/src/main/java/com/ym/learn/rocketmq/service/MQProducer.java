package com.ym.learn.rocketmq.service;

import com.alibaba.fastjson.JSON;
import com.ym.learn.rocketmq.config.RocketMqProperties;
import com.ym.learn.rocketmq.constant.RocketConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

import static com.ym.learn.rocketmq.constant.RocketConstant.TOPIC_DEFAULT;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/6 16:46
 * @Desc: 消息生产
 */
@Slf4j
@Component
public class MQProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private RocketMqProperties rocketMqProperties;

    private volatile String TOPIC_DEFAULT = RocketConstant.TOPIC_DEFAULT;

    @PostConstruct
    public void init(){
        log.debug("init rocketMqProperties:{}", JSON.toJSON(rocketMqProperties));
//        if (!StringUtils.isEmpty(rocketMqProperties.getProducerGroup())) {
//            TOPIC_DEFAULT = rocketMqProperties.getProducerGroup();
//        }
    }

    /**
     * 默认的发送消息
     * @param message
     */
    public void sendNormalMessage(Object message){
        log.debug("sendNormalMessage message:{}", JSON.toJSON(message));
        rocketMQTemplate.send(TOPIC_DEFAULT, MessageBuilder.withPayload(message).build());
    }

    /**
     * 发送同步消息
     * @param message
     * @return
     */
    public SendResult sendSyncMessage(String message){
        SendResult sendResult = rocketMQTemplate.syncSend(TOPIC_DEFAULT, MessageBuilder.withPayload(message).build());
        log.debug("sendSyncMessage result:{}", JSON.toJSON(sendResult));
        return sendResult;
    }
    /**
     * 发送异步消息
     * @param message
     */
    public void sendAsyncMessage(String message, SendCallback sendCallback) {
        rocketMQTemplate.asyncSend(TOPIC_DEFAULT, MessageBuilder.withPayload(message).build(), sendCallback);
    }
    /**
     * 发送
     * @param message
     * @param delayTime
     */
    public void sendSyncDelayMessage(String message, long delayTime) {
        log.debug("sendSyncDelayMessage delayTime:{}", delayTime);
        rocketMQTemplate.syncSend(TOPIC_DEFAULT,MessageBuilder.withPayload(message).build(),delayTime);
    }
    /**
     * 发送单向消息，不管结果
     * @param message
     * @param delayTime
     */
    public void sendOneWayMsg(String msgBody) {
        rocketMQTemplate.sendOneWay(TOPIC_DEFAULT, MessageBuilder.withPayload(msgBody).build());
    }

}
