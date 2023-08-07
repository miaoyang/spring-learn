package com.ym.learn.quartz.job;

import com.ym.learn.quartz.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Yangmiao
 * @Date: 2023/6/7 10:38
 * @Desc: CRON表达式定时任务执行
 */
@Component
@Slf4j
public class CronProcessJob implements Job {
    @Autowired
    private ScheduleService scheduleService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String data = jobDataMap.getString("data");
        log.info("CRON表达式任务执行：{}",data);
    }
}
