package com.kedu.project.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.BoardDTO;
import com.kedu.project.dto.ScheduleDTO;

@Repository
public class ScheduleDAO {

	@Autowired
	private SqlSession myBatis;

	// insert
	public int insertSchedule (ScheduleDTO scheduleDTO) {

		int result = myBatis.insert ("Schedule.insertSchedule",scheduleDTO);

		return result;
	}

	// 카테고리별 일정 목록
	public List<ScheduleDTO> getSchedulesByCategory(String category) {
	    return myBatis.selectList("Schedule.getSchedulesByCategory", category);
	}
	// 카테고리 all
	public List<ScheduleDTO> getAllSchedules() {
	    return myBatis.selectList("Schedule.getAllSchedules");
	}

	// detail
	public ScheduleDTO getDetail (int seq) {
		return myBatis.selectOne ("Schedule.getDetail", seq);
	}

	// delete
	public void deleteSchedule (int seq) {
		myBatis.delete ("Schedule.deleteSchedule", seq);
	}

	// modify
	public void modifySchedule (ScheduleDTO scheduleDTO) {
		myBatis.update("Schedule.modifySchedule", scheduleDTO);
	}
}
