package com.kedu.project.dto;
import java.sql.Timestamp;

public class AttendanceDTO {
	private int seq;
	private int cnt;
	private String member_id;
	private Timestamp start_time;
	private Timestamp end_time;
	private String start_status;
	private String end_startus;
	
	
	public AttendanceDTO() {
		
	}
	
	public AttendanceDTO(int seq, String member_id, Timestamp start_time, Timestamp end_time, String start_status,
			String end_startus, int cnt) {
		super();
		this.seq = seq;
		this.member_id = member_id;
		this.start_time = start_time;
		this.end_time = end_time;
		this.start_status = start_status;
		this.end_startus = end_startus;
		this.cnt = cnt;
	}


	public String getStart_status() {
		return start_status;
	}


	public void setStart_status(String start_status) {
		this.start_status = start_status;
	}


	public String getEnd_startus() {
		return end_startus;
	}


	public void setEnd_startus(String end_startus) {
		this.end_startus = end_startus;
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
	
}
