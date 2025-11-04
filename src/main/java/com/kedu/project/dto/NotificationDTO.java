package com.kedu.project.dto;

import java.sql.Timestamp;

public class NotificationDTO {
	private int seq;
	private String sender_id;   // 알림 받을 유저 ID
    private String receiver_id;         // 알림 종류 (ex. "MAIL", "TASK", "BOARD")
    private String type;      // 표시할 메시지
    private String message;
    private String is_read;    // 생성 시간
    private Timestamp created_at;
	public NotificationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NotificationDTO(int seq, String sender_id, String receiver_id, String type, String message, String is_read,
			Timestamp created_at) {
		super();
		this.seq = seq;
		this.sender_id = sender_id;
		this.receiver_id = receiver_id;
		this.type = type;
		this.message = message;
		this.is_read = is_read;
		this.created_at = created_at;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getSender_id() {
		return sender_id;
	}
	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}
	public String getReceiver_id() {
		return receiver_id;
	}
	public void setReceiver_id(String receiver_id) {
		this.receiver_id = receiver_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getIs_read() {
		return is_read;
	}
	public void setIs_read(String is_read) {
		this.is_read = is_read;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
    
    
	
    
    
}
