package com.kedu.project.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.ApprovalDTO;
import com.kedu.project.dto.MemberDTO;

@Repository
public class ApprovalDAO {

	@Autowired
	private SqlSession mybatis;
	
	
	public List<ApprovalDTO> selectAll(){
		return mybatis.selectList("approval.selectAll");
	}
	
	
	//상태변경(승인,반려 등)에 따라 나오게하는 동작
	public List<ApprovalDTO> selectByStatus(String status){
		return mybatis.selectList("approval.selectByStatus",status);
	}
	
	//내용 추가
	public int insert(ApprovalDTO dto) {
		return mybatis.insert("approval.insert",dto);
	}
	
	//임시 저장 추가
	
	public int tempinsert(ApprovalDTO dto) {
		return mybatis.insert("approval.tempinsert",dto);
	}
	
	//내용 보여줄거임
	public ApprovalDTO getDetail(int seq) {
		return mybatis.selectOne("approval.getDetail",seq);
	}
	
	
	//작성자가 temp문서를 가지고있는가?
	
	public ApprovalDTO findTempByWriter(String writerId) {
		return mybatis.selectOne("approval.findTempByWriter",writerId);
	}
	
	//임시 저장  내용 업데이트
	public int tempUpdate(ApprovalDTO dto) {
		return mybatis.update("approval.tempUpdate",dto);
	}
	
	
	//결재선 직급 불러올거야
	public List<MemberDTO> selelctApprovalCandidates(){
		return mybatis.selectList("approval.selectApprovalCandidates");
	}
	
	 //같은 부서 상급상사 출력
    public List<MemberDTO> selectApproverCandidates(Map<String,Object> param){
    	return  mybatis.selectList("approval.selectApproverCandidates",param);
    }
    
    //결재선 조회
    public List<Map<String, Object>> selectApprovalLine(String approvalId){
        return mybatis.selectList("approval.selectApprovalLine", approvalId);
    }

    //  참조자 조회
    public List<MemberDTO> selectReferenceList(int seq) {
        return mybatis.selectList("approval.selectReferenceList", seq);
    }
    
    
    //결재선 조회
    public int insertApprovalLine(int approvalId, String approvalUserId, String approverId, Integer orderNo, String status) {
        Map<String, Object> param = new HashMap<>();
        param.put("approvalId", approvalId);       // 문서번호
        param.put("approvalNumber", approvalId);   // 문서번호 그대로 재사용
        param.put("approvalUserId", approvalUserId); // 작성자 ID
        param.put("approverId", approverId);       // 결재자 ID
        param.put("orderNo", orderNo);             // 순번 (참조자는 null)
        param.put("status", status);  
        return mybatis.insert("approval.insertApprovalLine", param);
    }
    public boolean existsReject(String approvalId){
        return mybatis.selectOne("approval.existsReject", approvalId);
    }

    // 모든 결재자 승인 완료 여부 체크
    public boolean isAllApproved(String approvalId){
        return mybatis.selectOne("approval.isAllApproved", approvalId);
    }

    //승인한 결재자 존재 여부 체크
    public boolean existsApproved(String approvalId){
        return mybatis.selectOne("approval.existsApproved", approvalId);
    }
    
    
    
    public int approveLine(String approvalId, String userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("approvalId", approvalId);
        param.put("userId", userId);
        return mybatis.update("approval.approveLine", param);
    }

    public String selectNextApprover(String approvalId) {  
        return mybatis.selectOne("approval.selectNextApprover", approvalId);
    }
    public void updateDocStatus(String approvalId, String status) {
        Map<String, Object> param = new HashMap<>();
        param.put("approvalId", approvalId);
        param.put("status", status);
        mybatis.update("approval.updateDocStatus", param);
    }
    
    public List<ApprovalDTO> selectMyWaitList(String userId){
        return mybatis.selectList("approval.selectMyWaitList", userId);
    }

    public List<ApprovalDTO> selectMyScheduledList(String userId){
        return mybatis.selectList("approval.selectMyScheduledList", userId);
    }
    
    public int rejectLine(String approvalId, String userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("approvalId", approvalId);
        param.put("userId", userId);
        return mybatis.update("approval.rejectLine", param);
    }
    
    public void rejectLine(String approvalId, String userId, String reason){
        Map<String, Object> param = new HashMap<>();
        param.put("approvalId", approvalId);
        param.put("userId", userId);
        param.put("reason", reason);
        mybatis.update("approval.rejectLine", param);
    }

    
	
	
	
	
}
