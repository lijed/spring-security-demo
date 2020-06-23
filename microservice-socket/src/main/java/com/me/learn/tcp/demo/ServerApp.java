/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.me.learn.tcp.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Administrator
 * @date 2020/6/18 12:47
 * Project Name: microservice-socket
 */
public class ServerApp {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        BufferedReader bufferedReader = null;

        try {
            //创建一个serverSocket在端口8080监听客户请求
            serverSocket = new ServerSocket(8080);


            //使用accept阻塞等待用户请求，有客户请求到来，则产生一个socket并继续执行
            Socket socket = serverSocket.accept();

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = bufferedReader.readLine();
            System.out.println("line = " + line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
