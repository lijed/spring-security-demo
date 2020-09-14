/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.me.learn.zk.curator.zkquartzleaderlatch;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author Administrator
 * @date 2020/9/13 23:58
 * Project Name: zk-quartz-leader-latch
 */
@Configuration
public class QuartzConfig {

    @Bean
    public ZkSchedulerFactoryBean schedulerFactoryBean() throws Exception {
        ZkSchedulerFactoryBean schedulerFactoryBean = new ZkSchedulerFactoryBean();
        schedulerFactoryBean.setJobDetails(jobDetail());
        schedulerFactoryBean.setTriggers(trigger());
        return schedulerFactoryBean;
    }

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(QuartzJob.class).storeDurably().build();
    }

    @Bean
    public Trigger trigger() {
        SimpleScheduleBuilder simpleScheduleBuilder
                = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(1)
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(jobDetail()).withSchedule(simpleScheduleBuilder).build();
    }

}
