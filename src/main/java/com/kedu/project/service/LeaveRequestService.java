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
        Double used = leaveRequestDAO.selectRemainLeave(memberId); // ✅ Double 로 받기
        double usedLeave = (used != null) ? used : 0.0;
        return 15.0 - usedLeave;
    }

   
    public void insertLeaveRequest(LeaveRequestPayload payload, String memberId, String rank) {
    	System.out.println("랭크:"+rank);
        List<LeaveRequestPayload.Item> items = payload.getItems();

        String start  = items.get(0).getDate();
        String end    = items.get(items.size() - 1).getDate();
        String reason = items.get(0).getReason();
        String code   = items.get(0).getType();

        // ✅ 차감 규칙
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

        
        if ("사장".equals(rank)) {
            dto.setStatus("A"); // 승인 상태로 저장
            leaveRequestDAO.insertLeaveRequest(dto);

      
            if (useDays > 0) {
                leaveRequestDAO.updateUsedLeave(memberId, useDays);
            }
            return;
        }

      
        dto.setStatus("N"); // 승인 대기
        leaveRequestDAO.insertLeaveRequest(dto);
    }
}

	


