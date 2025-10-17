package com.kedu.project.dto;

public class ContactsDTO {

	private int seq;
	private	String user_id;
	private	String name;
	private	String phone;
	private	String email;
	private	String type;
	private	String team;
	private	String jobRank;
	


	public ContactsDTO() {}

	public ContactsDTO(int seq, String user_id, String name, String phone, String email, String type, String team,
			String jobRank) {
		super();
		this.seq = seq;
		this.user_id = user_id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.type = type;
		this.team = team;
		this.jobRank = jobRank;
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



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getTeam() {
		return team;
	}



	public void setTeam(String team) {
		this.team = team;
	}



	public String getJobRank() {
		return jobRank;
	}



	public void setJobRank(String jobRank) {
		this.jobRank = jobRank;
	}





}
