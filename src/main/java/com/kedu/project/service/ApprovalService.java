package com.kedu.project.service;
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
	
	public int insert(ApprovalDTO dto) {

	    // 1) 문서 저장 → seq 자동 생성됨
	    dao.insert(dto);

	    String writer = dto.getWriter(); // 작성자 ID

	 // 2) 결재선 저장
	    if(dto.getApprovers() != null) {
	        int order = 1;
	        for(MemberDTO m : dto.getApprovers()) {
	            dao.insertApprovalLine(dto.getSeq(), dto.getWriter(), m.getId(), order, "N");
	            order++;
	        }
	    }

	    // 3) 참조자 저장 (order = null)
	    if(dto.getReferenceList() != null) {
	        for(MemberDTO m : dto.getReferenceList()) {
	            dao.insertApprovalLine(dto.getSeq(), dto.getWriter(), m.getId(), null, "N");
	        }
	    }

	    return dto.getSeq();
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

		    // 1) 승인 처리 (해당 결재자만 Y 로 변경)
		    dao.approveLine(approvalId, userId);

		    // 2) 반려자가 있는지 먼저 체크
		    boolean rejectedExists = dao.existsReject(approvalId);
		    if(rejectedExists){
		        dao.updateDocStatus(approvalId, "REJECTED");
		        return;
		    }

		    // 3) 모든 결재자가 승인됐는지 체크
		    boolean allApproved = dao.isAllApproved(approvalId);
		    if(allApproved){
		        dao.updateDocStatus(approvalId, "APPROVED");
		        return;
		    }

		    // 4) 승인된 사람이 한 명이라도 있고, 아직 남은 결재자가 있다 → 진행중
		    boolean anyApproved = dao.existsApproved(approvalId);
		    if(anyApproved){
		        dao.updateDocStatus(approvalId, "CHECKING");
		        return;
		    }

		    // 5) 결재자만 존재하고 아직 아무도 승인X → 예정(PENDING)
		    dao.updateDocStatus(approvalId, "PENDING");
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

		    // 2) 문서 상태를 바로 전체 반려로 변경
		    dao.updateDocStatus(approvalId, "REJECTED");
		}
		
	
		
		
	 //test
	 
	
	
	
	
}