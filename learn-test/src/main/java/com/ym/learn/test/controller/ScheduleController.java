package com.ym.learn.test.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.ym.learn.core.api.R;
import com.ym.learn.quartz.job.CronProcessJob;
import com.ym.learn.quartz.job.SendEmailJob;
import com.ym.learn.quartz.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Yangmiao
 * @Date: 2023/6/7 22:12
 * @Desc: 定时任务
 */
@RestController
@RequestMapping("/schedule")
@Api(tags = "定时任务")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation(value = "定时发送邮件")
    @PostMapping("/sendEmail")
    public R sendEmail(@RequestParam String startTime,@RequestParam String data){
        DateTime date = DateUtil.parse(startTime, DatePattern.NORM_DATE_FORMAT);
        String jobName = scheduleService.scheduleFixTimeJob(SendEmailJob.class, date, data);
        return R.ok(jobName);
    }

    @ApiOperation(value = "CRON表达式调度任务")
    @PostMapping("/scheduleJob")
    public R schedule(@RequestParam String cron, @RequestParam String data){
        String jobName = scheduleService.scheduleJob(CronProcessJob.class, cron, data);
        return R.ok(jobName);
    }

    @ApiOperation("取消定时任务")
    @PostMapping("/cancelScheduleJob")
    public R cancelScheduleJob(@RequestParam String jobName) {
        Boolean success = scheduleService.cancelScheduleJob(jobName);
        return R.ok(success);
    }
}
