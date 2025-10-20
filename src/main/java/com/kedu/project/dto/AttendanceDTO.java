package com.kedu.project.dto;
import java.sql.Timestamp;

public class AttendanceDTO {
	private int seq;
	private String member_id;
	private Timestamp start_time;
	private Timestamp end_time;
	private String status;
	private int cnt;
	
	public AttendanceDTO() {
		
	}
	

	public AttendanceDTO(int seq, String member_id, Timestamp start_time, Timestamp end_time, String status, int cnt) {
		super();
		this.seq = seq;
		this.member_id = member_id;
		this.start_time = start_time;
		this.end_time = end_time;
		this.status = status;
		this.cnt = cnt;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
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
	public Timestamp getStart_time() {
		return start_time;
	}
	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}
	public Timestamp getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
