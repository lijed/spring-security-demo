package org.example;

import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Vector;

public class FtpUtil {

	private static FtpUtil INSTANCE = new FtpUtil();

	public static FtpUtil getInstance() {
		return INSTANCE;
	}

	Session sshSession;
	public static Boolean SUCCESS = true;
	public static Boolean FAIL = false;
	
	private ChannelSftp connect(SFtp sftp) throws JSchException {
		ChannelSftp sftpChannel = null;

		JSch jsch = new JSch();
		jsch.getSession(sftp.getUserName(), sftp.getHostName(), sftp.getPort());
		sshSession = jsch.getSession(sftp.getUserName(), sftp.getHostName(),
				sftp.getPort());

		sshSession.setPassword(sftp.getPwd());
		Properties sshConfig = new Properties();
		sshConfig.put("StrictHostKeyChecking", "no");
		sshConfig.put("PreferredAuthentications", "publickey,keyboard-interactive,password"); //auth
		sshSession.setConfig(sshConfig);
		
		Integer timeOut = null;
		if (timeOut == null) {
			timeOut = 59000;
		}
		sshSession.connect(timeOut);

		Channel channel = sshSession.openChannel("ftp");
		channel.connect();
		sftpChannel = (ChannelSftp) channel;

		return sftpChannel;
	}

	public Boolean testConnection(SFtp sftp) throws JSchException, SftpException {
		Boolean response = FtpUtil.FAIL;
		if (sftp == null)
			sftp = new SFtp();
		ChannelSftp sftpChannel = connect(sftp);
		if (sftpChannel != null) {
			sftpChannel.cd(sftp.getPath());
			sftpChannel.mkdir("test");
			sftpChannel.rmdir("test");
			response = FtpUtil.SUCCESS;
		}
		closeSSHSession();
		return response;
	}

	public void closeSSHSession() {
		if (this.sshSession != null) {
			sshSession.disconnect();
		}
	}

	public void upload(SFtp sftp, String uploadFile) throws JSchException,
			SftpException, FileNotFoundException {
		if (sftp == null)
			sftp = new SFtp();
		ChannelSftp sftpChannel = connect(sftp);
		sftpChannel.cd(sftp.getPath());
		File file = new File(uploadFile);
		sftpChannel.put(new FileInputStream(file), file.getName());
		closeSSHSession();
	}

	public void download(SFtp sftp, String downloadFile, String saveFile)
			throws JSchException, SftpException, FileNotFoundException {
		if (sftp == null)
			sftp = new SFtp();
		ChannelSftp sftpChannel = connect(sftp);
		sftpChannel.cd(sftp.getPath());
		File file = new File(saveFile);
		sftpChannel.get(downloadFile, new FileOutputStream(file));
		closeSSHSession();
	}

	public void delete(SFtp sftp, String deleteFile) throws JSchException,
			SftpException {
		if (sftp == null)
			sftp = new SFtp();
		ChannelSftp sftpChannel = connect(sftp);
		sftpChannel.cd(sftp.getPath());
		sftpChannel.rm(deleteFile);
		closeSSHSession();
	}

	public Vector listFiles(SFtp sftp) throws JSchException, SftpException {
		if (sftp == null)
			sftp = new SFtp();
		ChannelSftp sftpChannel = connect(sftp);
		Vector vector = sftpChannel.ls(sftp.getPath());
		closeSSHSession();
		return vector;
	}
}