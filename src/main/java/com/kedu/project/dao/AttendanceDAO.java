package com.kedu.project.dao;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.AttendanceDTO;

@Repository
public class AttendanceDAO {
	@Autowired
	private SqlSession mybatis;
	
	
	//근태 근황 횟수 보여줄거임
	public List<AttendanceDTO> CountSelect()
	{
		return mybatis.selectList("attendance.CountSelect");
	}
	
	

}
