package com.kedu.project.dto;


public class AuthDTO {
<<<<<<< HEAD

	private String id;
	private String pw;
	
	
	public AuthDTO() {
		
	}
	
=======
	private String id;
	private String pw;
	
	public AuthDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
>>>>>>> 29a2a6656e479b23beb52c2383a0387d3cd8d6d8
	public AuthDTO(String id, String pw) {
		super();
		this.id = id;
		this.pw = pw;
	}
<<<<<<< HEAD


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPw() {
		return pw;
	}


=======
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
>>>>>>> 29a2a6656e479b23beb52c2383a0387d3cd8d6d8
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	
<<<<<<< HEAD
	
	
	
=======
>>>>>>> 29a2a6656e479b23beb52c2383a0387d3cd8d6d8
}
