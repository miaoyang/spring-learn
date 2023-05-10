package com.ym.learn.test.rocketmq.controller;

import com.ym.learn.core.api.R;
import com.ym.learn.rocketmq.service.MQProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/8 16:40
 * @Desc:
 */
@RestController
@RequestMapping("/rocketmq")
@Api(tags = "RocketMqController")
@Slf4j
public class RocketMqController {

    @Autowired
    private MQProducer mqProducer;

    @RequestMapping(value = "/sendNormalMessage",method = RequestMethod.POST)
    @ApiOperation(value = "发送普通消息")
    public void sendNormalMessage(String message){
        log.error("发送普通消息：{}",message);
        mqProducer.sendNormalMessage(message);
    }

    @RequestMapping(value = "/sendSyncMessage",method = RequestMethod.POST)
    @ApiOperation(value = "发送同步消息")
    public R sendSyncMessage(String message){
        log.info("发送同步消息：{}",message);
        SendResult sendResult = mqProducer.sendSyncMessage(message);
        return R.ok(sendResult);
    }

    @RequestMapping(value = "/helloMQ",method = RequestMethod.GET)
    public R helloMQ(){

        return R.ok("Hello RocketMQ");
    }

}
