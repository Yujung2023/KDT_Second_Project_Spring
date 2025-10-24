package com.kedu.project.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kedu.project.dto.LeaveRequestDTO;

@Mapper
public interface LeaveRequestDAO {
	  // 잔여 연차 조회 (15 - 사용연차)
	Double selectRemainLeave(String memberId);

	   //  휴가 신청 INSERT
	   int insertLeaveRequest(LeaveRequestDTO dto);

	   //  사용 연차 누적
	   int updateUsedLeave(String memberId, Double used);
}


