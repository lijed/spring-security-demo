package org.example;

public class SFtp {

    private String hostName;
    private Integer port;
    private String userName;
	private String pwd;
    private String path;
    private Integer timeOut; // time unit: milisecond

    public SFtp() {}
	public SFtp(String hostName, Integer port, String pwd, String path) {
		this.hostName = hostName;
		this.port = port;
		this.pwd = pwd;
		this.path = path;
	}

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }
}
