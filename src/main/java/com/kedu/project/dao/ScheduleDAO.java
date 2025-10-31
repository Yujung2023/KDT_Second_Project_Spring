package com.kedu.project.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.BoardDTO;
import com.kedu.project.dto.ScheduleDTO;

@Repository
public class ScheduleDAO {

	@Autowired
	private SqlSession myBatis;

	// 일정 등록
	public int insertSchedule(ScheduleDTO scheduleDTO) {
		return myBatis.insert("Schedule.insertSchedule", scheduleDTO);
	}

	// 카테고리별 조회
	public List<ScheduleDTO> getSchedulesByCategory(String category, String loginId) {
		Map<String, Object> param = new HashMap<>();
		param.put("category", category);
		param.put("loginId", loginId);
		return myBatis.selectList("Schedule.getSchedulesByCategory", param);
	}

	// 전체 일정 조회
	public List<ScheduleDTO> getAllSchedules(String loginId) {
		return myBatis.selectList("Schedule.getAllSchedules", loginId);
	}

	// 일정 상세
	public ScheduleDTO getDetail(int seq) {
		return myBatis.selectOne("Schedule.getDetail", seq);
	}

	// 일정 수정
	public void modifySchedule(ScheduleDTO scheduleDTO) {
		myBatis.update("Schedule.modifySchedule", scheduleDTO);
	}
	
	// 일정 삭제
	public void deleteSchedule(int seq) {
		myBatis.delete("Schedule.deleteSchedule", seq);
	}

}
