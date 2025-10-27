package com.kedu.project.dto;

public class LoginLogDTO {
    private int seq;
    private String user_id;
    private String login_time;
    private String state;
    private String channel; //웹인지 모바일인지
    private String os;
    private String browser;
    private String ip_address;
	public LoginLogDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginLogDTO(int seq, String user_id, String login_time, String state, String channel, String os,
			String browser, String ip_address) {
		super();
		this.seq = seq;
		this.user_id = user_id;
		this.login_time = login_time;
		this.state = state;
		this.channel = channel;
		this.os = os;
		this.browser = browser;
		this.ip_address = ip_address;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getLogin_time() {
		return login_time;
	}
	public void setLogin_time(String login_time) {
		this.login_time = login_time;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
	
    
}