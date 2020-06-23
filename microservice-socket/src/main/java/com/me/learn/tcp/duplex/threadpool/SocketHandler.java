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
 * @date 2020/6/18 21:17
 * Project Name: microservice-socket
 */
public class SocketHandler implements  Runnable {
    private Socket socket;

    public SocketHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader is = null;
        BufferedReader sin = null;
        PrintWriter os = null;
        try {
            //由socket对象得到输入流，并构造相应的bufferedReader对象
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            os = new PrintWriter(socket.getOutputStream());

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
        } catch (IOException e) {
            System.out.println("Error: " + e);
        } finally {
            if (os != null) {
                os.close();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sin != null) {
                try {
                    sin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
