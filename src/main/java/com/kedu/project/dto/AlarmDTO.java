package com.kedu.project.dto;

import java.sql.Timestamp;

public class AlarmDTO {
	private int seq;
	private String member_id;
	private String alarm_code;
	private String comments;
	private String is_read;
	private Timestamp createAlarm;
	public AlarmDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AlarmDTO(int seq, String member_id, String alarm_code, String comments, String is_read,
			Timestamp createAlarm) {
		super();
		this.seq = seq;
		this.member_id = member_id;
		this.alarm_code = alarm_code;
		this.comments = comments;
		this.is_read = is_read;
		this.createAlarm = createAlarm;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getAlarm_code() {
		return alarm_code;
	}
	public void setAlarm_code(String alarm_code) {
		this.alarm_code = alarm_code;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getIs_read() {
		return is_read;
	}
	public void setIs_read(String is_read) {
		this.is_read = is_read;
	}
	public Timestamp getCreateAlarm() {
		return createAlarm;
	}
	public void setCreateAlarm(Timestamp createAlarm) {
		this.createAlarm = createAlarm;
	}
	
	
}
