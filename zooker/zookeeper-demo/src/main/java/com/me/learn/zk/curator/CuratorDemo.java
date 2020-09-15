/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.me.learn.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.RetrySleeper;
import org.apache.curator.framework.AuthInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CreateBuilder;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.imps.CreateBuilderImpl;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.springframework.cglib.core.DefaultGeneratorStrategy;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static com.me.learn.zk.curator.ZkConfig.SERVER_STR;

/**
 * @author Administrator
 * @date 2020/9/6 21:22
 * Project Name: zookeeper-demo
 */
public class CuratorDemo {


    public static void main(String[] args) throws Exception {
        List<AuthInfo> authInfos = new ArrayList<>();
//        AuthInfo authInfo = new AuthInfo("digest", "u1:u1".getBytes());
        AuthInfo authInfo2 = new AuthInfo("digest", "u2:u2".getBytes());
//        authInfos.add(authInfo);
        authInfos.add(authInfo2);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(SERVER_STR)
                .authorization(authInfos)
                .sessionTimeoutMs(5000)  //会话超时时间
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)) //1000 initial amount of time to wait between retries
                .connectionTimeoutMs(4000) //连接超时是时间
                .build();
        curatorFramework.start();

//        createZkNode(curatorFramework, "/first_node/first_node_sub_1", "test");

//        getZkNode(curatorFramework, "/first_node");

//        createZkNodeWithAync(curatorFramework, "/test1", "test");


//          createZkNodeWithAcl(curatorFramework, "/first_auth_acl", "acl");

          getZkNodeWithAcl(curatorFramework, "/first_auth_acl");
    }


    private static void createZkNode(CuratorFramework curatorFramework, String path, String data) throws Exception {
        String createBuilder = curatorFramework.create().creatingParentsIfNeeded().forPath(path, data.getBytes());
        System.out.println("createBuilder = " + createBuilder);
    }

    private static void getZkNode(CuratorFramework curatorFramework, String path) throws Exception {
        byte[] result = curatorFramework.getData().forPath(path);
        System.out.println("path: " + path + " = " + new String(result));
    }

    private static String createZkNodeWithAync(CuratorFramework curatorFramework, String path, String data) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        curatorFramework.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .inBackground(new BackgroundCallback() {
                                  @Override
                                  public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                                      System.out.println(Thread.currentThread().getName() + "- state: " + event.getStat());
                                      countDownLatch.countDown();
                                  }
                              }
                )
                .forPath(path, data.getBytes());
        // add some logics
        System.out.println("before create node request is submitted");
        countDownLatch.await();
        System.out.println("after: create node request is processed");
        return null;
    }

    /**
     * 在创建CuratorFramework时需要是定AuthInfo @See {@link CuratorFramework.authorization()}
     */
    private static String getZkNodeWithAcl(CuratorFramework curatorFramework, String path) throws Exception {
        String result = new String(curatorFramework.getData()
                .forPath(path));
        return result;
    }

    private static String createZkNodeWithAcl(CuratorFramework curatorFramework, String path, String data) throws Exception {
        List<ACL> acls = new ArrayList<>();
        ACL acl = new ACL(ZooDefs.Perms.CREATE | ZooDefs.Perms.DELETE, new Id("digest",
                DigestAuthenticationProvider.generateDigest("u1:u1")));

        ACL acl1 = new ACL(ZooDefs.Perms.ALL | ZooDefs.Perms.DELETE, new Id("digest",
                DigestAuthenticationProvider.generateDigest("u2:u2")));
        acls.add(acl);
        acls.add(acl1);
        String result = curatorFramework.create().creatingParentsIfNeeded()
                .withACL(acls)
                .forPath(path, data.getBytes());
        return result;
    }
}
