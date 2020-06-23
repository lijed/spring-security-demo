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
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author Administrator
 * @date 2020/6/20 10:42
 * Project Name: microservice-socket
 */
public class NewIoServer {
    public static void main(String[] args) throws InterruptedException {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8080));

            //默认情况是阻塞的
            serverSocketChannel.configureBlocking(false); //设置为非阻塞

            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    socketChannel.read(byteBuffer);
                    System.out.println("Client Data: " + new String(byteBuffer.array())); // 读取数据

                    // 写数据
                    byteBuffer.flip();
                    socketChannel.write(ByteBuffer.wrap("Hello Client, I am NIO server".getBytes()));
                    byteBuffer.clear();

                } else {
                    Thread.sleep(1000);
                    System.out.println("连接未就绪");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
