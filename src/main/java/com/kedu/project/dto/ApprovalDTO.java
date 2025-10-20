package com.kedu.project.dto;

import java.sql.Timestamp;

import java.sql.Timestamp;

public class ApprovalDTO {
	private int seq;
	private String title;
	private String writer;
	private String comments;
	private String status;
	private Timestamp writeDate;

	
	public ApprovalDTO() {
		
	}
	
	public ApprovalDTO(int seq, String title, String writer, String comments, Timestamp writeDate, String status) {
		super();
		this.seq = seq;
		this.title = title;
		this.writer = writer;
		this.comments = comments;
		this.writeDate = writeDate;
		this.status = status;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Timestamp getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Timestamp writeDate) {
		this.writeDate = writeDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
