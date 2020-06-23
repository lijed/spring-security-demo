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
import java.net.Socket;

/**
 * @author Administrator
 * @date 2020/6/18 16:49
 * Project Name: microservice-socket
 */
public class ClientApp2 {
    public static void main(String[] args) {
        Socket socket = null;
        PrintWriter os = null;
        BufferedReader sin = null;
        BufferedReader is = null;

        try {
            socket = new Socket("127.0.0.1", 8080);

            //从系统输入流开始读入数据
            sin = new BufferedReader(new InputStreamReader(System.in));

            //由socket对象得到输出流，并构造PrintBuffer对象
            os = new PrintWriter( socket.getOutputStream());

            //有socket对象得到输入流，并构造出相应的bufferReader对象
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String readLine;
            readLine = sin.readLine();
            while(!"bye".equalsIgnoreCase(readLine)) {
                //从标准输入设备读入字符串并输出到server
                os.write(readLine);
                os.println();
                //刷新输出流，使server马上收到改字符串
                os.flush();

                System.out.println("Client = " + readLine);

                //IO阻塞，如果接收到server端结果，马上输出
                System.out.println("Server: " + is.readLine());

                readLine = sin.readLine();
            }

            os.close();
            is.close();
            sin.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
