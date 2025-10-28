package com.kedu.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.BoardDAO;
import com.kedu.project.dao.ScheduleDAO;
import com.kedu.project.dto.BoardDTO;
import com.kedu.project.dto.ScheduleDTO;

@Service
public class ScheduleService {

	@Autowired
	ScheduleDAO scheduleDAO;

	// insert
	public int insertSchedule(ScheduleDTO scheduleDTO) {
		 scheduleDAO.insertSchedule(scheduleDTO);
		 return scheduleDTO.getSeq();
	}

	// 카테고리별 일정 목록
	public List<ScheduleDTO> getSchedulesByCategory(String category) {
		return scheduleDAO.getSchedulesByCategory(category);
	}
	
	// 카테고리 all
	public List<ScheduleDTO> getAllSchedules() {
		return scheduleDAO.getAllSchedules();
	}

	// detail
	public ScheduleDTO getDetail(int seq) {
		return scheduleDAO.getDetail(seq);
	}

	// delete
	public void deleteSchedule (int seq) {
		scheduleDAO.deleteSchedule(seq);
	}

	// modify
	public void modifySchedule (int seq , ScheduleDTO scheduleDTO) {
		scheduleDTO.setSeq(seq);
		scheduleDAO.modifySchedule(scheduleDTO);
	}
}
