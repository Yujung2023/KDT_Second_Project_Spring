package com.kedu.project.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.ApprovalDocDAO;
import com.kedu.project.dao.LeaveRequestDAO;
import com.kedu.project.dto.LeaveRequestDTO;
import com.kedu.project.dto.LeaveRequestPayload;
import com.kedu.project.dto.LeaveStatusDTO;

import jakarta.transaction.Transactional;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestDAO leaveRequestDAO;

    @Autowired
    private ApprovalDocDAO approvalDocDAO;

    // ✅ 휴가 신청
    @Transactional
    public void insertLeaveRequest(LeaveRequestPayload payload, String memberId, String rank) {

        List<LeaveRequestPayload.Item> items = payload.getItems();
        String start = items.get(0).getDate();
        String end = items.get(items.size() - 1).getDate();
        String reason = items.get(0).getReason();
        String code = items.get(0).getType();

        double useDays = ("annual".equals(code)) ? items.size()
                : ("half_am".equals(code) || "half_pm".equals(code)) ? 0.5
                : 0.0;
        
        double remainLeave = getRemainLeave(memberId);
        if (remainLeave < useDays) {  
            throw new RuntimeException("잔여 연차가 부족하여 휴가를 신청할 수 없습니다.");
        }

        String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String random = String.valueOf((int) (Math.random() * 9000) + 1000);

        String approvalId = "LEAVE_" + memberId + "_" + today + "_" + random;
        String approvalNumber = "AP-" + today + "-" + random;

        LeaveRequestDTO dto = new LeaveRequestDTO();
        dto.setMember_id(memberId);
        dto.setLeave_count(useDays);
        dto.setLeave_code(code);
        dto.setStart_leave_time(Timestamp.valueOf(start + " 00:00:00"));
        dto.setEnd_leave_time(Timestamp.valueOf(end + " 23:59:59"));
        dto.setReason(reason);
        dto.setApproval_id(approvalId);

        // ✅ 사장/부사장 바로 승인
        if ("사장".equals(rank) || "부사장".equals(rank)) {
            dto.setStatus("APPROVED");
            leaveRequestDAO.insertLeaveRequest(dto);
            leaveRequestDAO.updateUsedLeave(memberId, useDays);
            return;
        }

        // ✅ 일반 직원 → 대기 상태 저장
        dto.setStatus("WAITING");
        leaveRequestDAO.insertLeaveRequest(dto);
        
        //결재자 저장
        List<LeaveRequestPayload.Approver> approvers = payload.getApprovers();
        for (int i = 0; i < approvers.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("approvalId", approvalId);
            map.put("approvalNumber", approvalNumber);
            map.put("approvalUserId", memberId);
            map.put("approverId", approvers.get(i).getId());
            map.put("orderNo", i + 1);
            map.put("status", "WAITING");   // 결재자 상태
            map.put("docStatus", "WAITING"); // 문서 상태 ✅ 추가
            approvalDocDAO.insertApprovalLine(map);
        }

        // 참조자 저장
        List<LeaveRequestPayload.Approver> references = payload.getReferences();
        if (references != null) {
            for (LeaveRequestPayload.Approver ref : references) {
                Map<String, Object> map = new HashMap<>();
                map.put("approvalId", approvalId);
                map.put("approvalNumber", approvalNumber);
                map.put("approvalUserId", memberId);
                map.put("approverId", ref.getId());
                map.put("orderNo", null);       // NULL ✅
                map.put("status", "REFERENCE"); // 개인 상태
                map.put("docStatus", "WAITING"); // 문서 상태 유지 ✅ (핵심)
                approvalDocDAO.insertApprovalLine(map);
            }
        }
    }

    // ✅ 승인 처리
    @Transactional
    public void approveLeave(int seq, String loginUserId) {

        // 1) approval_id 조회
        String approvalId = approvalDocDAO.getApprovalIdBySeq(seq);

        // 2) 현재 결재 순번 확인
        String currentApprover = approvalDocDAO.getCurrentApprover(approvalId);
        if (!loginUserId.equals(currentApprover)) {
            throw new RuntimeException("아직 결재 순번이 아닙니다.");
        }

        // 3) 현재 결재자 승인 처리
        approvalDocDAO.updateApproverStatus(approvalId, loginUserId, "APPROVED");

        // 4) 다음 결재자 존재 확인
        String nextApprover = approvalDocDAO.getNextApprover(approvalId, loginUserId);

        if (nextApprover == null) {
            // ✅ 마지막 승인 → 최종 승인
            leaveRequestDAO.updateLeaveStatus(seq, "APPROVED");

            // ✅ 연차 차감
            Double leaveCount = leaveRequestDAO.getLeaveCount(seq);
            leaveRequestDAO.updateUsedLeave(
                    leaveRequestDAO.selectRequesterId(seq),
                    leaveCount
            );

        } else {
            // ✅ 다음 결재자로 넘어감 → 상태 확인중
            leaveRequestDAO.updateLeaveStatus(seq, "CHECKING");
        }
    }

    // ✅ 반려 처리
    @Transactional
    public void rejectLeave(int seq, String loginUserId, String reason) {

        String approvalId = approvalDocDAO.getApprovalIdBySeq(seq);

        String currentApprover = approvalDocDAO.getCurrentApprover(approvalId);
        if (!loginUserId.equals(currentApprover)) {
            throw new RuntimeException("아직 결재 순번이 아닙니다.");
        }

        // 결재선 반려 처리
        approvalDocDAO.updateApproverStatus(approvalId, loginUserId, "REJECTED");

        // 휴가요청 자체 반려 처리
        leaveRequestDAO.updateLeaveStatus(seq, "REJECTED");

        // 반려 사유 기록
        approvalDocDAO.insertRejectReason(seq, reason);
    }

    public double getRemainLeave(String memberId) {
        Double used = leaveRequestDAO.selectRemainLeave(memberId);
        double usedLeave = (used != null) ? used : 0.0;
        return 15.0 - usedLeave;
    }

    public List<LeaveStatusDTO> getLeaveStatus(String rankCode, String memberId, String deptCode) {
        return leaveRequestDAO.selectLeaveStatus(rankCode, memberId, deptCode);
    }
}
