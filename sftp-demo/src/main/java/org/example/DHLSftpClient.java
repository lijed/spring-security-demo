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

import javax.management.ObjectName;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * @date 2020/9/17 16:57
 * Project Name: sftp-demo
 */
public class DHLSftpClient {

    public static final String OUT_BOUND_WORKING_DIR = "/out/work/ppt/outcnf";

    @lombok.SneakyThrows
    public static void main(String[] args) throws SftpException, JSchException, IOException {
        SFtp sFtp = new SFtp();
        sFtp.setHostName("sftp3.dhl.com");
        sFtp.setUserName("wkg2ytpc");
        sFtp.setPwd("xASfSrt&92Y22i2c");
        sFtp.setPort(4222);
        sFtp.setPath(OUT_BOUND_WORKING_DIR);
//        SFtpServices sFtpServices = new SFtpServices();
//        Vector fileNames = sFtpServices.listViaSftp(sFtp);
//        for (Object fileName : fileNames) {
//            System.out.println("fileName = " + fileName.toString());
//        }

        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            final Set<String> fileSet = new HashSet<String>();
            ChannelSftp.LsEntrySelector selector =   new ChannelSftp.LsEntrySelector() {
                @Override
                public int select(ChannelSftp.LsEntry entry) {
                    if (!(".".equals(entry.getFilename()) || "..".equals(entry.getFilename()))) {
                        fileSet.add(entry.getFilename());
                    }
                    return 0;
                }
            };

            SFtpUtil.getInstance().listFiles(sFtp, OUT_BOUND_WORKING_DIR, selector);
            for (String fileName : fileSet) {
                System.out.println("fileName = " + fileName);
                SFtpUtil.getInstance().download(sFtp, fileName, "C:\\Users\\Administrator\\IdeaProjects\\sftp-demo\\importfolder\\" + fileName + i);
            }
        }
        Long endTime = System.currentTimeMillis();

        System.out.println("total spend: " + TimeUnit.MILLISECONDS.toSeconds(endTime-startTime));
    }
}
