/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.me.learn.tcp.nio.version1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author Administrator
 * @date 2020/6/20 10:43
 * Project Name: microservice-socket
 */
public class NewIoClient {
    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
//            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("localhost", 8080));

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            byteBuffer.put("Hello, I am socketChannel client".getBytes());
            byteBuffer.flip(); // limit: position, position: 0,  mark: -1
            socketChannel.write(byteBuffer);
            byteBuffer.clear(); //position: 0, limit: capacity, mark: -1

            int i = socketChannel.read(byteBuffer); //阻塞
            //收到数据的个数
            if (i > 0 ) {
                System.out.println("收到服务器端的数据:" +  new String(byteBuffer.array()));
            } else{
                System.out.println("服务端没反应");
            }
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
