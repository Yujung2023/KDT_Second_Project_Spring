package com.kedu.project.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.ApprovalDAO;
import com.kedu.project.dto.ApprovalDTO;
import com.kedu.project.dto.MemberDTO;

import jakarta.transaction.Transactional;

@Service
public class ApprovalService {

	@Autowired
	public ApprovalDAO dao;
	
	public List<ApprovalDTO> selectAll(){
		return dao.selectAll();
	}
	
	public List<ApprovalDTO> selectByStatus(String status){
		return dao.selectByStatus(status);
	}
	
	
	@Transactional
	public int insert(ApprovalDTO dto) {

	    dao.insert(dto);
	    int approvalId = dto.getSeq();
	    String writerId = dto.getWriter_id();

	    // 결재자 저장
	    if (dto.getApprovers() != null) {
	        for (MemberDTO m : dto.getApprovers()) {

	            Integer orderNo = m.getApproverOrder(); // ✅ 여기만 바꾸면 해결

	            dao.insertApprovalLine(
	                approvalId,
	                writerId,
	                m.getId(),
	                orderNo,
	                (orderNo != null && orderNo == 1) ? "N" : "P"
	            );
	        }
	    }

	    // 참조자 저장
	    if (dto.getReferenceList() != null) {
	        for (MemberDTO m : dto.getReferenceList()) {
	            dao.insertApprovalLine(
	                approvalId,
	                writerId,
	                m.getId(),
	                null,
	                "N"
	            );
	        }
	    }

	    return approvalId;
	}
	
	public int tempinsert(ApprovalDTO dto) {
		System.out.println("임시저장");
		return dao.tempinsert(dto);
	}
	
	public ApprovalDTO getDetail(int seq) {
		System.out.println("보여줄게");
		return dao.getDetail(seq);
	}
	
	public void saveTemp(ApprovalDTO dto) {
		ApprovalDTO temp=dao.findTempByWriter(dto.getWriter());
		if(temp==null) {
			dao.tempinsert(dto);
		}else {
			dto.setSeq(temp.getSeq());
			dao.tempUpdate(dto);
		}
	}
	
	public ApprovalDTO getTemp(String writerId) {
		return dao.findTempByWriter(writerId);
	}
	
	public List<MemberDTO> getApprovalCandidates(){
		return dao.selelctApprovalCandidates();
	}
	
	 public List<MemberDTO> selectReferenceList(Map<String, Object> param) {
	        return dao.selectApproverCandidates(param);
	    }
	 
	 
	 
	 public List<Map<String, Object>> selectApprovalLine(String approvalId){
		    return dao.selectApprovalLine(approvalId);
		}

		public List<MemberDTO> selectReferenceList(int seq){
		    return dao.selectReferenceList(seq);
		}
		
	
		@Transactional
		public void approve(String approvalId, String userId) {

		    // 1) 현재 결재자 승인 처리
		    dao.approveLine(approvalId, userId);

		    // ✅ 2) 다음 결재자 자동 활성화 (P → N)
		    dao.activateNextApprover(approvalId);

		    // 3) 반려자가 있는지 먼저 확인 → 문서 전체 상태 즉시 반려
		    if (dao.existsReject(approvalId)) {
		        dao.updateDocStatus(approvalId, "REJECTED");
		        return;
		    }

		    // 4) 모든 결재자가 승인 완료 상태인지 확인 → 기안 완료
		    if (dao.isAllApproved(approvalId)) {
		        dao.updateDocStatus(approvalId, "APPROVED");
		        return;
		    }

		    // 5) 승인한 사람이 있고 + 결재자가 남았을 경우 → 결재 진행 중
		    if (dao.existsApproved(approvalId)) {
		        dao.updateDocStatus(approvalId, "PROCESSING");
		        return;
		    }

		    // 6) 첫 결재 전 → 대기 상태
		    dao.updateDocStatus(approvalId, "WAIT");
		}


		
		public List<ApprovalDTO> getMyWaitList(String userId){
		    return dao.selectMyWaitList(userId);
		}

		public List<ApprovalDTO> getMyScheduledList(String userId){
		    return dao.selectMyScheduledList(userId);
		}
		
		@Transactional
		public void reject(String approvalId, String userId, String reason){
		    // 1) 해당 결재자를 반려 처리
		    dao.rejectLine(approvalId, userId, reason);

		    // 2) 문서 상태를 바로 전체 반려 상태로 변경
		    dao.updateDocStatus(approvalId, "REJECTED");
		}
		
		public List<ApprovalDTO> getDocsVisibleTo(String loginId) {
		    return dao.selectDocsVisibleTo(loginId);
		}
		
	
	
		
	
	
	
	
	
}