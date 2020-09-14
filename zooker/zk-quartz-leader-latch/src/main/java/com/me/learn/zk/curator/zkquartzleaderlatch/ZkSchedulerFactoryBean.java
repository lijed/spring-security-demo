/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.me.learn.zk.curator.zkquartzleaderlatch;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Administrator
 * @date 2020/9/14 0:04
 * Project Name: zk-quartz-leader-latch
 */
public class ZkSchedulerFactoryBean extends SchedulerFactoryBean {

    private static CuratorFramework zkClient;
    private static String ZOOKEEPER_CONNECTION_STRING="192.168.3.111:2181";
    private LeaderLatch leaderLatch; //leader选举的api
    private static final String LEADER_PATH="/quartzleader";

    Logger LOG= LoggerFactory.getLogger(ZkSchedulerFactoryBean.class);

    public ZkSchedulerFactoryBean() throws Exception {
        this.setAutoStartup(false);
        leaderLatch = new LeaderLatch(getClient(), LEADER_PATH);
        leaderLatch.addListener(new ZkJobLeaderLatchListener(getIp(), this));
        leaderLatch.start();
    }

    private CuratorFramework getClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        zkClient =  CuratorFrameworkFactory.builder().connectString(ZOOKEEPER_CONNECTION_STRING).retryPolicy(retryPolicy).build();
        zkClient.start();
        return zkClient;
    }

    @Override
    protected void startScheduler(Scheduler scheduler, int startupDelay) throws SchedulerException {
        if (this.isAutoStartup()) {
            super.startScheduler(scheduler, startupDelay);
        }
    }

    @Override
    public void destroy() throws SchedulerException {
        CloseableUtils.closeQuietly(leaderLatch);
        super.destroy();
    }

    private String getIp(){
        String host=null;
        try {
            host= InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return host;
    }

    class ZkJobLeaderLatchListener implements LeaderLatchListener {

        private String ip;
        private SchedulerFactoryBean schedulerFactoryBean;

        public ZkJobLeaderLatchListener(String ip) {
            this.ip = ip;
        }

        public ZkJobLeaderLatchListener(String ip, SchedulerFactoryBean schedulerFactoryBean) {
            this.ip = ip;
            this.schedulerFactoryBean = schedulerFactoryBean;
        }

        @Override
        public void isLeader() {
            LOG.info("ip:{} 成为leader，执行scheduler~",ip);
            schedulerFactoryBean.setAutoStartup(true);
            schedulerFactoryBean.start(); //启动（抢占到leader的节点去执行任务）
        }

        @Override
        public void notLeader() {
            LOG.info("ip:{} 不是leader，停止scheduler~",ip);
            schedulerFactoryBean.setAutoStartup(false);
            schedulerFactoryBean.stop(); //启动（抢占到leader的节点去执行任务）
        }
    }
}
