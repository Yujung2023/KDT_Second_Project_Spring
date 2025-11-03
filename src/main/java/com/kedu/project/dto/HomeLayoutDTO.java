package com.kedu.project.dto;

public class HomeLayoutDTO {
	private String user_Id;
    private String layout_Json;
    private String updated_At;
	public HomeLayoutDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HomeLayoutDTO(String user_Id, String layout_Json, String updated_At) {
		super();
		this.user_Id = user_Id;
		this.layout_Json = layout_Json;
		this.updated_At = updated_At;
	}
	public String getUser_Id() {
		return user_Id;
	}
	public void setUser_Id(String user_Id) {
		this.user_Id = user_Id;
	}
	public String getLayout_Json() {
		return layout_Json;
	}
	public void setLayout_Json(String layout_Json) {
		this.layout_Json = layout_Json;
	}
	public String getUpdated_At() {
		return updated_At;
	}
	public void setUpdated_At(String updated_At) {
		this.updated_At = updated_At;
	}
    
	
}
