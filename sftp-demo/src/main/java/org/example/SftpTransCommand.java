package org.example;

import java.util.List;

public class SftpTransCommand  {
	
	
	private static final long serialVersionUID = 4585459979649866258L;
	
	/***
	 * Upload need filePath, will upload file from local.
	 * Download need filePath, will download file the local.
	 */
	String filePath;
	
	/***
	 * Upload need fileName, will upload file to server.
	 * Download need fileName, will download file from server.
	 * Delete need fileName, this file server file
	 */
	String fileName;
	
	/**
	 * OPERATION_UPLOAD="upload";
	 * OPERATION_DOWNLOAD="download";
	 * OPERATION_DELETE="delete";
	 * OPERATION_LIST="list";
	 */
	String operate;
	
	SFtp originSFtp;
	
	List<SFtp> targetSFtpList;
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public SFtp getOriginSFtp() {
		return originSFtp;
	}
	public void setOriginSFtp(SFtp originSFtp) {
		this.originSFtp = originSFtp;
	}
	public List<SFtp> getTargetSFtpList() {
		return targetSFtpList;
	}
	public void setTargetSFtpList(List<SFtp> targetSFtpList) {
		this.targetSFtpList = targetSFtpList;
	}
}
