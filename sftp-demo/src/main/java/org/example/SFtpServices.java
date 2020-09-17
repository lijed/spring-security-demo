package org.example;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import java.io.IOException;
import java.util.Vector;

public class SFtpServices {

    public void uploadViaSftp(SFtp sftp, SftpTransCommand sftpTransCommand)
            throws IOException, JSchException, SftpException {
        String realPath = sftpTransCommand.getFilePath() + sftpTransCommand.getFileName();
        new SFtpUtil().upload(sftp, realPath);
    }

    public Boolean testViaSftp(SFtp sftp) throws JSchException, SftpException {
        Boolean response = new SFtpUtil().testConnection(sftp);
        return response;
    }

    public void downloadViaSftp(SFtp sftp, SftpTransCommand sftpTransCommand)
            throws IOException, JSchException, SftpException {
        String realPath = sftpTransCommand.getFilePath() + sftpTransCommand.getFileName();
        new SFtpUtil().download(sftp, sftpTransCommand.getFileName(), realPath);
    }

    public void deleteViaSftp(SFtp sftp, SftpTransCommand sftpTransCommand)
            throws JSchException, SftpException {
        new SFtpUtil().delete(sftp, sftpTransCommand.getFileName());
    }

    public Vector listViaSftp(SFtp sftp)
            throws JSchException, SftpException {
        Vector response = new SFtpUtil().listFiles(sftp);
        return response;
    }

}