package com.kedu.project.dto;

import java.sql.Timestamp;

public class MailArchiveDTO {
	private long seq;              // 메일 번호
    private String managerId;      // 관리자 ID
    private String managerIp;
    private String senderId;       // 보낸 사람 ID
    private String recipientId;    // 받는 사람 ID
    private String keyword;			// 검색 내용
    private String period; 			//검색 기간
    private Timestamp created_at;  // 내역 생성 시간.
	public MailArchiveDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MailArchiveDTO(long seq, String managerId, String managerIp, String senderId, String recipientId,
			String keyword, String period, Timestamp created_at) {
		super();
		this.seq = seq;
		this.managerId = managerId;
		this.managerIp = managerIp;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.keyword = keyword;
		this.period = period;
		this.created_at = created_at;
	}
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public String getManagerIp() {
		return managerIp;
	}
	public void setManagerIp(String managerIp) {
		this.managerIp = managerIp;
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
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
    
    
    
	
	
	
	
}