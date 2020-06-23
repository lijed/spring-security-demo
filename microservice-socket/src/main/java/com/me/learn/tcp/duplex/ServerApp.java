/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.me.learn.tcp.duplex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
        BufferedReader is = null;
        BufferedReader sin = null;
        Socket socket = null;
        try {
            //创建一个serverSocket在端口8080监听客户请求
            serverSocket = new ServerSocket(8080);

            //使用accept阻塞等待用户请求，有客户请求到来，则产生一个socket并继续执行
            socket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println("Error" + e);
        }

        try {

            //由socket对象得到输入流，并构造相应的bufferedReader对象
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter os = new PrintWriter(socket.getOutputStream());
            
            sin = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Client" + is.readLine());

            String line = sin.readLine();
            while (!"bye".equalsIgnoreCase(line)) {
                os.println(line);
                os.flush(); //使client端马上收到改字符串

                System.out.println("server: " + line);

                //从client端读入一行字符串，并打印在标准输出上
                System.out.println("Client: " + is.readLine());

                //server端从系统标准输入读入一个字符串
                line = sin.readLine();
            }

            os.close();
            is.close();
            sin.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
