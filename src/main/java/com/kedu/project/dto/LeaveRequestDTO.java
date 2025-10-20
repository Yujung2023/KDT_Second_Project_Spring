package com.kedu.project.dto;

import java.sql.Timestamp;

public class LeaveRequestDTO {

	private int seq;
	private String member_id;
	private int leave_count;
	private String leave_code;
	private Timestamp start_leave_time;
	private Timestamp end_leave_time;
	private String reason;
	private String status;
	
	public void LeaveRequstDTO() {
		
	}
	
	
	public LeaveRequestDTO(int seq, String member_id, int leave_count, String leave_code, Timestamp start_leave_time,
			Timestamp end_leave_time, String reason, String status) {
		super();
		this.seq = seq;
		this.member_id = member_id;
		this.leave_count = leave_count;
		this.leave_code = leave_code;
		this.start_leave_time = start_leave_time;
		this.end_leave_time = end_leave_time;
		this.reason = reason;
		this.status = status;
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
	public int getLeave_count() {
		return leave_count;
	}
	public void setLeave_count(int leave_count) {
		this.leave_count = leave_count;
	}
	public String getLeave_code() {
		return leave_code;
	}
	public void setLeave_code(String leave_code) {
		this.leave_code = leave_code;
	}
	public Timestamp getStart_leave_time() {
		return start_leave_time;
	}
	public void setStart_leave_time(Timestamp start_leave_time) {
		this.start_leave_time = start_leave_time;
	}
	public Timestamp getEnd_leave_time() {
		return end_leave_time;
	}
	public void setEnd_leave_time(Timestamp end_leave_time) {
		this.end_leave_time = end_leave_time;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
