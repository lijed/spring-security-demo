package org.example;

import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

public class SFtpUtil {

	private static SFtpUtil INSTANCE = new SFtpUtil();

	public static SFtpUtil getInstance() {
		return INSTANCE;
	}

	Session sshSession;
	public static Boolean SUCCESS = true;
	public static Boolean FAIL = false;
	
	private ChannelSftp connect(SFtp sftp) throws JSchException {

		JSch jsch = new JSch();
		jsch.getSession(sftp.getUserName(), sftp.getHostName(), sftp.getPort());
		sshSession = jsch.getSession(sftp.getUserName(), sftp.getHostName(), sftp.getPort());
		sshSession.setPassword(sftp.getPwd());

		//auth
		Properties sshConfig = new Properties();
		sshConfig.put("StrictHostKeyChecking", "no");
		sshConfig.put("PreferredAuthentications", "publickey,keyboard-interactive,password");
		sshSession.setConfig(sshConfig);
		
		Integer timeOut = sftp.getTimeOut();
		if (timeOut == null) {
			timeOut = 59000;
		}
		sshSession.connect(timeOut);

		Channel channel = sshSession.openChannel("sftp");
		channel.connect();
		ChannelSftp  sftpChannel = (ChannelSftp) channel;

		return sftpChannel;
	}

	public Boolean testConnection(SFtp sftp) throws JSchException, SftpException {
		Boolean response = SFtpUtil.FAIL;
		ChannelSftp sftpChannel = connect(sftp);
		if (sftpChannel != null) {
			sftpChannel.cd(sftp.getPath());
			sftpChannel.mkdir("test");
			sftpChannel.rmdir("test");
			response = SFtpUtil.SUCCESS;
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
			SftpException, IOException {
		ChannelSftp sftpChannel = connect(sftp);
		sftpChannel.cd(sftp.getPath());
		File file = new File(uploadFile);
		FileInputStream fileInputStream = new FileInputStream(file);
		sftpChannel.put(fileInputStream, file.getName());
		closeSSHSession();
		fileInputStream.close();
	}

	public void download(SFtp sftp, String downloadFile, String saveFile)
			throws JSchException, SftpException, IOException {
		download(sftp, downloadFile, saveFile, null);
	}
	public void download(SFtp sftp, String downloadFile, String saveFile, ChannelSftp sftpChannel)
			throws JSchException, SftpException, IOException {
		if (sftpChannel == null) {
			sftpChannel = connect(sftp);
		}
		sftpChannel.cd(sftp.getPath());
		File file = new File(saveFile);
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		sftpChannel.get(downloadFile, fileOutputStream);
		fileOutputStream.close();
		if (sftpChannel == null) {
			closeSSHSession();
		}
	}

	public void delete(SFtp sftp, String deleteFile) throws JSchException,
			SftpException {
		ChannelSftp sftpChannel = connect(sftp);
		sftpChannel.cd(sftp.getPath());
		sftpChannel.rm(deleteFile);
		closeSSHSession();
	}

	public Vector listFiles(SFtp sftp) throws JSchException, SftpException {
		ChannelSftp sftpChannel = connect(sftp);
		Vector vector = sftpChannel.ls(sftp.getPath());
		closeSSHSession();
		return vector;
	}

	public void listFiles(SFtp sftp, String targetFolder, ChannelSftp.LsEntrySelector selector) throws JSchException, SftpException {
		ChannelSftp sftpChannel = connect(sftp);
		sftpChannel.ls(sftp.getPath(), selector);
		closeSSHSession();
	}
	public void listFiles(SFtp sftp, String targetFolder, ChannelSftp.LsEntrySelector selector, ChannelSftp channelSftp) throws JSchException, SftpException {
		if (channelSftp == null) {
			channelSftp = connect(sftp);
		}
		channelSftp.ls(sftp.getPath(), selector);
		if (channelSftp == null) {
			closeSSHSession();
		}
	}

	public void listFilesAndDownload(SFtp sftp,  String downloadSourceFolder, String saveFolder) throws JSchException, SftpException, IOException {
		ChannelSftp sftpChannel = connect(sftp);
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

		listFiles(sftp, downloadSourceFolder, selector, sftpChannel);
		for (String fileName : fileSet) {
			System.out.println("fileName = " + fileName);
			download(sftp, downloadSourceFolder+ "/" + fileName, saveFolder + fileName, sftpChannel);
		}
		closeSSHSession();
	}
}