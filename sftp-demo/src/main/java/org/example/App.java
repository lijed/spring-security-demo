package org.example;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        SFtp sftp = new SFtp();
        sftp.setHostName("shipftp.ariix.com");
        sftp.setPath("Warehouse30/Processed_Test");
        sftp.setUserName("CanadaCartage");
        sftp.setPwd("CanCartage32!");
        sftp.setPort(22);

        try {
            Boolean isConnected  = SFtpUtil.getInstance().testConnection(sftp);
            System.out.println("isConnected = " + isConnected);

            try {
                SFtpUtil.getInstance().upload(sftp, "C:\\Users\\Administrator\\IdeaProjects\\sftp-demo\\src\\main\\java\\org\\example\\App.java");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }


    }
}
