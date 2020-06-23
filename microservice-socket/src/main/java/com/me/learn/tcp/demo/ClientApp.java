/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.me.learn.tcp.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Administrator
 * @date 2020/6/18 13:02
 * Project Name: microservice-socket
 */
public class ClientApp {
    public static void main(String[] args) {
        Socket socket = null;
        PrintWriter printWriter = null;
        try {
            socket = new Socket("127.0.0.1", 8080);
            printWriter = new PrintWriter( socket.getOutputStream(), true);
            printWriter.println("Hello, Jed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
