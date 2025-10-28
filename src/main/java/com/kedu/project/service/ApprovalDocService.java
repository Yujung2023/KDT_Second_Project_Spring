package com.kedu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kedu.project.dao.ApprovalDocDAO;
import com.kedu.project.dto.ApprovalDocDTO;
import java.util.List;
import java.util.Map;

@Service
public class ApprovalDocService {

    @Autowired
    private ApprovalDocDAO approvalDocDAO;

    /**
     * ❌ 전자결재 문서 생성은 지금 프로젝트에서 사용하지 않음
     *    → 빈 메서드로 둬서 혹시라도 호출되더라도 영향 없도록 처리
     */
    @Transactional
    public void createApprovalDoc(ApprovalDocDTO dto) {
        // 사용 안함 (LeaveRequest는 approval_line만 사용)
        System.out.println("⚠️ createApprovalDoc() 호출됨 – 현재 휴가 시스템은 approval_doc 사용하지 않음");
    }

    /**
     * ❌ approval_doc 상태 업데이트도 현재 사용 안함
     */
    @Transactional
    public void updateStatus(int seq, String status) {
        // 사용 안함
        System.out.println("⚠️ updateStatus() 호출됨 – 현재 휴가 시스템은 approval_doc 상태를 관리하지 않음");
    }

    /**
     * ❌ approval_doc 조회도 사용 안함
     */
    public ApprovalDocDTO getApprovalDoc(int seq) {
        return null; // 사용 안함
    }

    /**
     * ✅ 결재선 조회 (이건 LeaveStatus 모달에서 사용하므로 유지)
     */
    public List<Map<String, Object>> getApprovalLine(String approvalId) {
        return approvalDocDAO.selectApprovalLine(approvalId);
    }
}
