package com.kedu.project.dto;

import java.sql.Timestamp;

public class MemberDTO {
	private String id;
	private String password;
	private String name;
	private int zip_code; 
	private String address_line1;
	private String address_line2;
	private String status;
	private String employee_no;
	private String hire_date;
	private String job_code;
	private String rank_code; 
	private Timestamp created_time;
	
	public MemberDTO(String id, String password, String name, int zip_code, String address_line1, String address_line2,
			String status, String employee_no, String hire_date, String job_code, String rank_code,
			Timestamp created_time) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.zip_code = zip_code;
		this.address_line1 = address_line1;
		this.address_line2 = address_line2;
		this.status = status;
		this.employee_no = employee_no;
		this.hire_date = hire_date;
		this.job_code = job_code;
		this.rank_code = rank_code;
		this.created_time = created_time;
	}

	public MemberDTO() {
		super();
		// TODO Auto-generated constructor stub
	} 
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getZip_code() {
		return zip_code;
	}
	public void setZip_code(int zip_code) {
		this.zip_code = zip_code;
	}
	public String getAddress_line1() {
		return address_line1;
	}
	public void setAddress_line1(String address_line1) {
		this.address_line1 = address_line1;
	}
	public String getAddress_line2() {
		return address_line2;
	}
	public void setAddress_line2(String address_line2) {
		this.address_line2 = address_line2;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmployee_no() {
		return employee_no;
	}
	public void setEmployee_no(String employee_no) {
		this.employee_no = employee_no;
	}
	public String getHire_date() {
		return hire_date;
	}
	public void setHire_date(String hire_date) {
		this.hire_date = hire_date;
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
	public Timestamp getCreated_time() {
		return created_time;
	}
	public void setCreated_time(Timestamp created_time) {
		this.created_time = created_time;
	}
	
}

