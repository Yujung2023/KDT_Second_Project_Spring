package com.kedu.project.dto;

import java.sql.Timestamp;

public class TaskCommentDTO {
    private int seq;          // 댓글 번호 (PK)
    private int task_seq;     // 어떤 업무의 댓글인지 (FK)
    private String writer_id;  // 작성자 ID
    private String writer_name;  // 작성자 ID
    private String content;    // 댓글 내용
    private Timestamp created_at; // 작성일시
	public TaskCommentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TaskCommentDTO(int seq, int task_seq, String writer_id, String writer_name, String content,
			Timestamp created_at) {
		super();
		this.seq = seq;
		this.task_seq = task_seq;
		this.writer_id = writer_id;
		this.writer_name = writer_name;
		this.content = content;
		this.created_at = created_at;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getTask_seq() {
		return task_seq;
	}
	public void setTask_seq(int task_seq) {
		this.task_seq = task_seq;
	}
	public String getWriter_id() {
		return writer_id;
	}
	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}
	public String getWriter_name() {
		return writer_name;
	}
	public void setWriter_name(String writer_name) {
		this.writer_name = writer_name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
    
    
    
	
    
    
}