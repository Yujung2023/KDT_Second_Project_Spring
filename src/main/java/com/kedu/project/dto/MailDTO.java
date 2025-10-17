package com.kedu.project.dto;

import java.sql.Timestamp;

public class MailDTO {

	private int seq;
	private String user_id;
	private String senderId;
	private String recipientId;
	private String title;
	private String fileContent;
	private String content;
	private Timestamp sendDate;
	private String sendDateStr;



	public MailDTO() {}

	public MailDTO(int seq, String user_id, String senderId, 
			String recipientId, String title, String fileContent,String content, Timestamp sendDate) {
		super();
		this.seq = seq;
		this.user_id = user_id;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.title = title;
		this.fileContent = fileContent;
		this.content = content;
		this.sendDate = sendDate;
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
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getRecipientId() {
		return recipientId;
	}
	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getSendDate() {
		return sendDate;
	}

	public void setSendDate(Timestamp sendDate) {
		this.sendDate = sendDate;
	}



	public String getSendDateStr() {
	    return sendDateStr;
	}

	public void setSendDateStr(String sendDateStr) {
	    this.sendDateStr = sendDateStr;
	}

}
