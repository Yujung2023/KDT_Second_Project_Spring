package com.kedu.project.dto;

public class LeaveStatusDTO {
	  private int seq;
	    private String memberId;
	    private String leaveCode;
	    private String reason;
	    private String status;
	    private String approverId;
	    private String approvalId;
	    private String rejectReason;
	    private String rejectTime;
	    private String memberName;
		private String approverName;
	    private String rankCode;  // member에서 가져온 rank_code
	    private String approvalStatus;
	    private String startLeaveTime;
	    private String endLeaveTime;
	    
	    
		public String getMemberName() {
			return memberName;
		}
		public void setMemberName(String memberName) {
			this.memberName = memberName;
		}

	    
	    
	    public String getRejectReason() {
			return rejectReason;
		}
		public void setRejectReason(String rejectReason) {
			this.rejectReason = rejectReason;
		}
		public String getRejectTime() {
			return rejectTime;
		}
		public void setRejectTime(String rejectTime) {
			this.rejectTime = rejectTime;
		}
		public String getApprovalId() {
			return approvalId;
		}
		public void setApprovalId(String approvalId) {
			this.approvalId = approvalId;
		}

	    
	    public LeaveStatusDTO() {
	    	
	    }

		
	
		public LeaveStatusDTO(int seq, String memberId, String leaveCode, String reason, String status,
				String approverId, String approvalId, String rejectReason, String rejectTime, String memberName,
				String approverName, String rankCode, String approvalStatus, String startLeaveTime,
				String endLeaveTime) {
			super();
			this.seq = seq;
			this.memberId = memberId;
			this.leaveCode = leaveCode;
			this.reason = reason;
			this.status = status;
			this.approverId = approverId;
			this.approvalId = approvalId;
			this.rejectReason = rejectReason;
			this.rejectTime = rejectTime;
			this.memberName = memberName;
			this.approverName = approverName;
			this.rankCode = rankCode;
			this.approvalStatus = approvalStatus;
			this.startLeaveTime = startLeaveTime;
			this.endLeaveTime = endLeaveTime;
		}
		public int getSeq() {
			return seq;
		}
		public void setSeq(int seq) {
			this.seq = seq;
		}
		public String getMemberId() {
			return memberId;
		}
		public void setMemberId(String memberId) {
			this.memberId = memberId;
		}
		public String getLeaveCode() {
			return leaveCode;
		}
		public void setLeaveCode(String leaveCode) {
			this.leaveCode = leaveCode;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getApproverId() {
			return approverId;
		}
		public void setApproverId(String approverId) {
			this.approverId = approverId;
		}
		public String getApproverName() {
			return approverName;
		}
		public void setApproverName(String approverName) {
			this.approverName = approverName;
		}
		public String getRankCode() {
			return rankCode;
		}
		public void setRankCode(String rankCode) {
			this.rankCode = rankCode;
		}
		public String getApprovalStatus() {
			return approvalStatus;
		}
		public void setApprovalStatus(String approvalStatus) {
			this.approvalStatus = approvalStatus;
		}
		public String getStartLeaveTime() {
			return startLeaveTime;
		}
		public void setStartLeaveTime(String startLeaveTime) {
			this.startLeaveTime = startLeaveTime;
		}
		public String getEndLeaveTime() {
			return endLeaveTime;
		}
		public void setEndLeaveTime(String endLeaveTime) {
			this.endLeaveTime = endLeaveTime;
		}
}
