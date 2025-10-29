package com.kedu.project.dto;

import java.sql.Timestamp;

public class FilesDTO {

	private int seq;
	private String module_type;
	private int module_seq;
	private String sysname;
	private String orgname;
	private String contentType;
	private Timestamp createdAt;
	
	public FilesDTO() {}

	public FilesDTO(int seq, String module_type, int module_seq, String sysname, String orgname, String contentType,
			Timestamp createdAt) {
		super();
		this.seq = seq;
		this.module_type = module_type;
		this.module_seq = module_seq;
		this.sysname = sysname;
		this.orgname = orgname;
		this.contentType = contentType;
		this.createdAt = createdAt;
	}
	
	public FilesDTO(String module_type, int module_seq, String sysname, String orgname, String contentType) {
	    this.module_type = module_type;
	    this.module_seq = module_seq;
	    this.sysname = sysname;
	    this.orgname = orgname;
	    this.contentType = contentType;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getModule_type() {
		return module_type;
	}

	public void setModule_type(String module_type) {
		this.module_type = module_type;
	}

	public int getModule_seq() {
		return module_seq;
	}

	public void setModule_seq(int module_seq) {
		this.module_seq = module_seq;
	}

	public String getSysname() {
		return sysname;
	}

	public void setSysname(String sysname) {
		this.sysname = sysname;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}	
}
