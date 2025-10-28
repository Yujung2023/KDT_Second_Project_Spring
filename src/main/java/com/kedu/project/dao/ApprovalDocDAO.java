package com.kedu.project.dao;

import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.kedu.project.dto.ApprovalDocDTO;

@Repository
public class ApprovalDocDAO {

    private static final String NAMESPACE = "com.kedu.project.dao.ApprovalDocDAO.";

    @Autowired
    private SqlSessionTemplate sqlSession;

    // ✅ 결재문서 등록 (leave_request insert)
    public int insertApprovalDoc(ApprovalDocDTO dto) {
        return sqlSession.insert(NAMESPACE + "insertApprovalDoc", dto);
    }

    // ✅ 결재선 등록
    public int insertApprovalLine(Map<String, Object> param) {
        return sqlSession.insert(NAMESPACE + "insertApprovalLine", param);
    }

    // ✅ 단일 조회
    public ApprovalDocDTO selectApprovalDoc(int seq) {
        return sqlSession.selectOne(NAMESPACE + "selectApprovalDoc", seq);
    }

    // ✅ 상태 업데이트
    public int updateStatus(ApprovalDocDTO dto) {
        return sqlSession.update(NAMESPACE + "updateStatus", dto);
    }

    // ✅ 결재선 조회
    public List<Map<String, Object>> selectApprovalLine(String approvalId) {
        return sqlSession.selectList(NAMESPACE + "selectApprovalLine", approvalId);
    }
}
