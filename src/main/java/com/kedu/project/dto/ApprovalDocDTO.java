package com.kedu.project.dto;

import java.sql.Timestamp;

public class ApprovalDocDTO {

	 private int seq;
	    private String approvalId;
	    private String approvalNumber;
	    private String approvalUserId;
	    private String approverId;
	    private Integer approverOrder;
	    private String approverStatus;
	    private String status;
	    private Timestamp writeDate;
	    
	    public ApprovalDocDTO() {
	    	
	    }
	    
		public ApprovalDocDTO(int seq, String approvalId, String approvalNumber, String approvalUserId,
				String approverId, Integer approverOrder, String approverStatus, String status, Timestamp writeDate) {
			super();
			this.seq = seq;
			this.approvalId = approvalId;
			this.approvalNumber = approvalNumber;
			this.approvalUserId = approvalUserId;
			this.approverId = approverId;
			this.approverOrder = approverOrder;
			this.approverStatus = approverStatus;
			this.status = status;
			this.writeDate = writeDate;
		}
		public int getSeq() {
			return seq;
		}
		public void setSeq(int seq) {
			this.seq = seq;
		}
		public String getApprovalId() {
			return approvalId;
		}
		public void setApprovalId(String approvalId) {
			this.approvalId = approvalId;
		}
		public String getApprovalNumber() {
			return approvalNumber;
		}
		public void setApprovalNumber(String approvalNumber) {
			this.approvalNumber = approvalNumber;
		}
		public String getApprovalUserId() {
			return approvalUserId;
		}
		public void setApprovalUserId(String approvalUserId) {
			this.approvalUserId = approvalUserId;
		}
		public String getApproverId() {
			return approverId;
		}
		public void setApproverId(String approverId) {
			this.approverId = approverId;
		}
		public Integer getApproverOrder() {
			return approverOrder;
		}
		public void setApproverOrder(Integer approverOrder) {
			this.approverOrder = approverOrder;
		}
		public String getApproverStatus() {
			return approverStatus;
		}
		public void setApproverStatus(String approverStatus) {
			this.approverStatus = approverStatus;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public Timestamp getWriteDate() {
			return writeDate;
		}
		public void setWriteDate(Timestamp writeDate) {
			this.writeDate = writeDate;
		}

}
