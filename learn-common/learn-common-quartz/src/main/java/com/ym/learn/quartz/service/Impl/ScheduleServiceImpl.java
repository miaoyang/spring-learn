package com.ym.learn.quartz.service.Impl;

import cn.hutool.core.date.DateUtil;
import com.ym.learn.quartz.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @Author: Yangmiao
 * @Date: 2023/6/7 10:22
 * @Desc: 定时任务Service
 */
@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private Scheduler scheduler;

    private static final String GROUP_DEFAULT = "default_group";

    @Override
    public String scheduleJob(Class<? extends Job> jobBeanClass, String cron, String data) {
        String jobName = UUID.randomUUID().toString().replace("-", "");
        JobDetail jobDetail = JobBuilder.newJob(jobBeanClass)
                .withIdentity(jobName,GROUP_DEFAULT)
                .usingJobData("data",data)
                .build();
        //创建触发器，指定任务执行时间
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, GROUP_DEFAULT)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
        try {
            scheduler.scheduleJob(jobDetail,cronTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobName;
    }

    @Override
    public String scheduleFixTimeJob(Class<? extends Job> jobBeanClass, Date startTime, String data) {
        //日期转CRON表达式
        String startCron = String.format("%d %d %d %d %d ? %d",
                DateUtil.second(startTime),
                DateUtil.minute(startTime),
                DateUtil.hour(startTime, true),
                DateUtil.dayOfMonth(startTime),
                DateUtil.month(startTime) + 1,
                DateUtil.year(startTime));
        return scheduleJob(jobBeanClass, startCron, data);
    }

    @Override
    public Boolean cancelScheduleJob(String jobName) {
        boolean success = false;
        try {
            // 暂停触发器
            scheduler.pauseTrigger(new TriggerKey(jobName, GROUP_DEFAULT));
            // 移除触发器中的任务
            scheduler.unscheduleJob(new TriggerKey(jobName, GROUP_DEFAULT));
            // 删除任务
            scheduler.deleteJob(new JobKey(jobName, GROUP_DEFAULT));
            success = true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return success;
    }
}
