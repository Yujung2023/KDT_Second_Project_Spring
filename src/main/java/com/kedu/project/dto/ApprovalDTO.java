package com.kedu.project.dto;

import java.sql.Timestamp;
import java.util.List;

public class ApprovalDTO {
	private int seq;
	private String title;
	private String writer;
	private String comments;
	private String status;
	private Timestamp writeDate;
	private List<MemberDTO> approvers;
	private List<MemberDTO> referenceList;
	private String myStatus; 


	

	public String getMyStatus() {
		return myStatus;
	}

	public void setMyStatus(String myStatus) {
		this.myStatus = myStatus;
	}

	public List<MemberDTO> getApprovers() {
		return approvers;
	}

	public void setApprovers(List<MemberDTO> approvers) {
		this.approvers = approvers;
	}

	public List<MemberDTO> getReferenceList() {
		return referenceList;
	}

	public void setReferenceList(List<MemberDTO> referenceList) {
		this.referenceList = referenceList;
	}

	public ApprovalDTO() {
		
	}
	
	
	
	public ApprovalDTO(int seq, String title, String writer, String comments, String status, Timestamp writeDate,
			List<MemberDTO> approvers, List<MemberDTO> referenceList, String myStatus) {
		super();
		this.seq = seq;
		this.title = title;
		this.writer = writer;
		this.comments = comments;
		this.status = status;
		this.writeDate = writeDate;
		this.approvers = approvers;
		this.referenceList = referenceList;
		this.myStatus = myStatus;
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
