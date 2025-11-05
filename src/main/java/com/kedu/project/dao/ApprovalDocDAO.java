package com.kedu.project.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.ApprovalDocDTO;
import com.kedu.project.dto.MemberDTO;

@Repository
public class ApprovalDocDAO {

    private static final String NAMESPACE = "com.kedu.project.dao.ApprovalDocDAO.";

    @Autowired
    private SqlSessionTemplate sqlSession;
    
    public List<MemberDTO> selectReferenceList(Map<String, Object> param) {
        return sqlSession.selectList(NAMESPACE + "selectReferenceList", param);
    }

    // 결재문서 등록 (leave_request insert)
    public int insertApprovalDoc(ApprovalDocDTO dto) {
        return sqlSession.insert(NAMESPACE + "insertApprovalDoc", dto);
    }

    // 결재선 등록
    public int insertApprovalLine(Map<String, Object> param) {
        return sqlSession.insert(NAMESPACE + "insertApprovalLine", param);
    }

    //  단일 조회
    public ApprovalDocDTO selectApprovalDoc(int seq) {
        return sqlSession.selectOne(NAMESPACE + "selectApprovalDoc", seq);
    }

    //  상태 업데이트
    public int updateStatus(ApprovalDocDTO dto) {
        return sqlSession.update(NAMESPACE + "updateStatus", dto);
    }

    // 결재선 조회
    public List<Map<String, Object>> selectApprovalLine(String approvalId) {
        return sqlSession.selectList(NAMESPACE + "selectApprovalLine", approvalId);
    }
    
    // seq → approvalId 조회
    public String getApprovalIdBySeq(int seq) {
        return sqlSession.selectOne(NAMESPACE + "getApprovalIdBySeq", seq);
    }

    // 현재 결재 차례 담당자 (STATUS = 'N' 중 MIN(orderNo))
    public String getCurrentApprover(String approvalId) {
        return sqlSession.selectOne(NAMESPACE + "getCurrentApprover", approvalId);
    }

    // 다음 결재자
    public String getNextApprover(String approvalId, String currentApproverId) {
        Map<String, Object> param = Map.of(
            "approvalId", approvalId,
            "approverId", currentApproverId
        );
        return sqlSession.selectOne(NAMESPACE + "getNextApprover", param);
    }

    // 결재자 상태 변경 (A = 승인, R = 반려)
    public int updateApproverStatus(String approvalId, String approverId, String status) {
        Map<String, Object> param = Map.of(
            "approvalId", approvalId,
            "approverId", approverId,
            "status", status
        );
        return sqlSession.update(NAMESPACE + "updateApproverStatus", param);
    }

    // 반려 사유 기록 (선택)
    public int insertRejectReasonByApprovalId(String approvalId, String reason) {
        Map<String, Object> param = Map.of(
            "approvalId", approvalId,
 
            "reason", reason
        );
        
        
     

        return sqlSession.update(NAMESPACE + "insertRejectReasonByApprovalId", param);
    }
    
    //같은 부서 상급상사 출력
    public List<MemberDTO> selectApproverCandidates(Map<String,Object> param){
    	return sqlSession.selectList(NAMESPACE+"selectApproverCandidates",param);
    }
    
}
