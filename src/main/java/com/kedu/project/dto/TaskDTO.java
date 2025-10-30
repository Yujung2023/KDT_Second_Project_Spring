package com.kedu.project.dto;

public class TaskDTO {
	private int seq;
	private int group_seq;
	private String title;
	private String description;
	private String assignee_id;
	private String status;
	private String created_id;     
	private String created_at;    
	private String updated_at;
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getGroup_seq() {
		return group_seq;
	}
	public void setGroup_seq(int group_seq) {
		this.group_seq = group_seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAssignee_id() {
		return assignee_id;
	}
	public void setAssignee_id(String assignee_id) {
		this.assignee_id = assignee_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreated_id() {
		return created_id;
	}
	public void setCreated_id(String created_id) {
		this.created_id = created_id;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public TaskDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TaskDTO(int seq, int group_seq, String title, String description, String assignee_id, String status,
			String created_id, String created_at, String updated_at) {
		super();
		this.seq = seq;
		this.group_seq = group_seq;
		this.title = title;
		this.description = description;
		this.assignee_id = assignee_id;
		this.status = status;
		this.created_id = created_id;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	
}
