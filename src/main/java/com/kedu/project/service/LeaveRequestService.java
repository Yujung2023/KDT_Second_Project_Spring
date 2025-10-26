package com.kedu.project.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.LeaveRequestDAO;
import com.kedu.project.dto.LeaveRequestDTO;
import com.kedu.project.dto.LeaveRequestPayload;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestDAO leaveRequestDAO;

    public double getRemainLeave(String memberId) {
        Double used = leaveRequestDAO.selectRemainLeave(memberId);
        double usedLeave = (used != null) ? used : 0.0;
        return 15.0 - usedLeave;
    }

    public void insertLeaveRequest(LeaveRequestPayload payload, String memberId, String rank) {
        List<LeaveRequestPayload.Item> items = payload.getItems();

        String start  = items.get(0).getDate();
        String end    = items.get(items.size() - 1).getDate();
        String reason = items.get(0).getReason();
        String code   = items.get(0).getType();

        // ✅ 연차 차감 계산
        double useDays = 0.0;
        if ("annual".equals(code)) {
            useDays = items.size();
        } else if ("half_am".equals(code) || "half_pm".equals(code)) {
            useDays = 0.5;
        }

        // ✅ DTO 생성
        LeaveRequestDTO dto = new LeaveRequestDTO();
        dto.setMember_id(memberId);
        dto.setLeave_count(useDays);
        dto.setLeave_code(code);
        dto.setStart_leave_time(Timestamp.valueOf(start + " 00:00:00"));
        dto.setEnd_leave_time(Timestamp.valueOf(end   + " 23:59:59"));
        dto.setReason(reason);

        // ✅ 사장 / 부사장 자동 승인
        if ("사장".equals(rank) || "부사장".equals(rank)) {
            dto.setStatus("A"); // 승인 상태
            leaveRequestDAO.insertLeaveRequest(dto);
            leaveRequestDAO.updateUsedLeave(memberId, useDays);
            return;
        }

        // ✅ 일반 직원 → 결재대기
        dto.setStatus("N");
        leaveRequestDAO.insertLeaveRequest(dto);
    }

    // ✅ 휴가 현황 조회 (본인 or 관리자 조건)
    public List<LeaveRequestDTO> getLeaveStatus(String rankCode, String memberId, String deptCode){
        return leaveRequestDAO.selectLeaveStatus(rankCode, memberId, deptCode);
    }
}
