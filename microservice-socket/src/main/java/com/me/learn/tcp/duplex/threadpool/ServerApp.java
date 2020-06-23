/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.me.learn.tcp.duplex.threadpool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Administrator
 * @date 2020/6/18 12:47
 * Project Name: microservice-socket
 */
public class ServerApp {
    public static void main(String[] args) throws IOException {
        //Thread Pool
        ExecutorService executorService = Executors.newCachedThreadPool();

        ServerSocket serverSocket = null;
        try {
            //创建一个serverSocket在端口8080监听客户请求
            serverSocket = new ServerSocket(8080);

            //使用accept阻塞等待用户请求，有客户请求到来，则产生一个socket并继续执行
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(new SocketHandler(socket));
            }
        } catch (IOException e) {
            System.out.println("Error" + e);
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
