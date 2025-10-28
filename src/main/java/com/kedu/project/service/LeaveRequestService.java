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
    private ApprovalDocDAO approvalDocDAO; // ✅ 결재선 insert 용도로만 사용

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

        String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String random = String.valueOf((int) (Math.random() * 9000) + 1000);

        String approvalId = "LEAVE_" + memberId + "_" + today + "_" + random;
        String approvalNumber = "AP-" + today + "-" + random;  // ✅ 추가

        LeaveRequestDTO dto = new LeaveRequestDTO();
        dto.setMember_id(memberId);
        dto.setLeave_count(useDays);
        dto.setLeave_code(code);
        dto.setStart_leave_time(Timestamp.valueOf(start + " 00:00:00"));
        dto.setEnd_leave_time(Timestamp.valueOf(end + " 23:59:59"));
        dto.setReason(reason);
        dto.setApproval_id(approvalId);

        // ✅ 사장/부사장은 바로 승인
        if ("사장".equals(rank) || "부사장".equals(rank)) {
            dto.setStatus("A");
            leaveRequestDAO.insertLeaveRequest(dto);
            leaveRequestDAO.updateUsedLeave(memberId, useDays);
            return;
        }

        // ✅ 일반 직원 → 대기상태 저장
        dto.setStatus("N");
        leaveRequestDAO.insertLeaveRequest(dto);

        // ✅ 결재선 저장 (여기가 핵심 수정!!)
        List<LeaveRequestPayload.Approver> approvers = payload.getApprovers();
        for (int i = 0; i < approvers.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("approvalId", approvalId);
            map.put("approvalNumber", approvalNumber);   // ✅ 반드시 추가
            map.put("approvalUserId", memberId);
            map.put("approverId", approvers.get(i).getId());
            map.put("orderNo", i + 1);
            map.put("status", "WAITING");  // ✅ NULL 방지
            approvalDocDAO.insertApprovalLine(map);
        }
    }
    
    public double getRemainLeave(String memberId) {
        Double used = leaveRequestDAO.selectRemainLeave(memberId);
        double usedLeave = (used != null) ? used : 0.0;
        return 15.0 - usedLeave; // 기본연차 15일 - 사용연차
    }

    public List<LeaveStatusDTO> getLeaveStatus(String rankCode, String memberId, String deptCode) {
        return leaveRequestDAO.selectLeaveStatus(rankCode, memberId, deptCode);
    }
}
