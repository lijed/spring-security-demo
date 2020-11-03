/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package org.example;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * @date 2020/9/17 16:57
 * Project Name: sftp-demo
 */
public class ShopdevSftpDownload {

    public static final String OUT_BOUND_WORKING_DIR = "/out/work/ppt/outcnf";

    @lombok.SneakyThrows
    public static void main(String[] args) throws SftpException, JSchException, IOException {
        SFtp sFtp = new SFtp();
        sFtp.setHostName("172.16.1.198");
        sFtp.setUserName("tomcat");
        sFtp.setPwd("<yC7',Bi");
        sFtp.setPort(22);
        sFtp.setPath("/var/aws/xoffice/business/1701017/");

        Long startTime = System.currentTimeMillis();
        String fileName= "ID_1701017_trip1604390511156.png";
        SFtpUtil.getInstance().download(sFtp, fileName, "C:\\Users\\Administrator\\IdeaProjects\\sftp-demo\\importfolder\\" + fileName);
        Long endTime = System.currentTimeMillis();

        System.out.println("total spend: " + TimeUnit.MILLISECONDS.toSeconds(endTime-startTime));
    }
}
