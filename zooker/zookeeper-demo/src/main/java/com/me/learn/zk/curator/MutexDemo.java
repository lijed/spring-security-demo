/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.me.learn.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import static com.me.learn.zk.curator.ZkConfig.SERVER_STR;

/**
 * @author Administrator
 * @date 2020/9/6 21:52
 * Project Name: zookeeper-demo
 */
public class MutexDemo {


    public static void main(String[] args) {

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(SERVER_STR)
                .sessionTimeoutMs(5000)  //会话超时时间
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)) //1000 initial amount of time to wait between retries
                .connectionTimeoutMs(4000) //连接超时是时间
                .build();
        curatorFramework.start();

        InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework, "/mutexlocks");
        for (int i = 0; i <10; i++) {
            new Thread(() -> {
               final String name = Thread.currentThread().getName();
                System.err.println("The current thread: " + name + " 尝试去获得锁");
                try {
                    interProcessMutex.acquire();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.err.println(name + " 获得锁开始处理资源");
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    interProcessMutex.release();
                    System.err.println(name +  " 释放锁");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "thread - " + i).start();
        }
    }
}
