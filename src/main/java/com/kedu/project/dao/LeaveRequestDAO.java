package com.kedu.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kedu.project.dto.LeaveRequestDTO;
import com.kedu.project.dto.LeaveStatusDTO;

@Mapper
public interface LeaveRequestDAO {

    // 잔여 연차 조회 (15 - 사용연차)
    Double selectRemainLeave(String memberId);

    // 휴가 신청 INSERT
    int insertLeaveRequest(LeaveRequestDTO dto);

    // 사용 연차 누적
    int updateUsedLeave(String memberId, Double used);

    //  휴가현황 + 결재선 조회 (관리자용)
    List<LeaveStatusDTO> selectLeaveStatus(String rankCode, String memberId, String deptCode);
}