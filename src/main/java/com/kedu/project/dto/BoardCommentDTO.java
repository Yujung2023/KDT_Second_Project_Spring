package com.kedu.project.dto;

import java.sql.Timestamp;

public class BoardCommentDTO {

	private int seq;
	private int parent_seq;
	private String writer_id;
	private String comments;
	private Timestamp writeDate;
	
	public BoardCommentDTO() {}

	public BoardCommentDTO(int seq, int parent_seq, String writer_id, String comments, Timestamp writeDate) {
		super();
		this.seq = seq;
		this.parent_seq = parent_seq;
		this.writer_id = writer_id;
		this.comments = comments;
		this.writeDate = writeDate;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getParent_seq() {
		return parent_seq;
	}

	public void setParent_seq(int parent_seq) {
		this.parent_seq = parent_seq;
	}

	public String getWriter_id() {
		return writer_id;
	}

	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
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
	
}
