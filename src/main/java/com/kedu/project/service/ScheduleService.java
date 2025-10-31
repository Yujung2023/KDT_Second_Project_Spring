package com.kedu.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.BoardDAO;
import com.kedu.project.dao.ScheduleDAO;
import com.kedu.project.dto.BoardDTO;
import com.kedu.project.dto.ScheduleDTO;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ScheduleService {

	@Autowired
	ScheduleDAO scheduleDAO;

	// 일정 등록
	public int insertSchedule(ScheduleDTO scheduleDTO) {
		scheduleDAO.insertSchedule(scheduleDTO);
		return scheduleDTO.getSeq();
	}

	// 카테고리별 조회
	public List<ScheduleDTO> getSchedulesByCategory(String category, String loginId) {
		return scheduleDAO.getSchedulesByCategory(category, loginId);
	}

	// 전체 일정 조회
	public List<ScheduleDTO> getAllSchedules(String loginId) {
		return scheduleDAO.getAllSchedules(loginId);
	}

	// 일정 상세
	public ScheduleDTO getDetail(int seq) {
		return scheduleDAO.getDetail(seq);
	}

	// 일정 수정
	public void modifySchedule(int seq, ScheduleDTO scheduleDTO) {
	    scheduleDTO.setSeq(seq);
	    scheduleDAO.modifySchedule(scheduleDTO);
	}

	// 일정 삭제
	public void deleteSchedule(int seq) {
	    scheduleDAO.deleteSchedule(seq);
	}
}
