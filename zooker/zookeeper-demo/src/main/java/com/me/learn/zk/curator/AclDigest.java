/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.me.learn.zk.curator;

import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;

/**
 * @author Administrator
 * @date 2020/9/9 23:25
 * Project Name: zookeeper-demo
 */
public class AclDigest {

    /**
     * digest 内置鉴权模式
     * @param args
     * @throws NoSuchAlgorithmException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String result  = DigestAuthenticationProvider.generateDigest("jed:secret");
        System.out.println(result);


    }
}
