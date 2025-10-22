package com.kedu.project.dto;

public class ContactsDTO {

	private int seq;
	private	String user_id;
	private	String name;
	private	String phone;
	private	String email;
	private	String type;
	private	String job_code;
	private	String rank_code;
	


	public ContactsDTO() {}

	public ContactsDTO(int seq, String user_id, String name, String phone, String email, String type, String job_code,
			String rank_code) {
		super();
		this.seq = seq;
		this.user_id = user_id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.type = type;
		this.job_code = job_code;
		this.rank_code = rank_code;
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



	public String getJob_code() {
		return job_code;
	}



	public void setJob_code(String job_code) {
		this.job_code = job_code;
	}



	public String getRank_code() {
		return rank_code;
	}



	public void setRank_code(String rank_code) {
		this.rank_code = rank_code;
	}





}
