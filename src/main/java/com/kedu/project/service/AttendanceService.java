package com.kedu.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.AttendanceDAO;
import com.kedu.project.dto.AttendanceDTO;

@Service
public class AttendanceService {

	
	@Autowired
	private AttendanceDAO dao;
	
	//근태 현황
	public List<AttendanceDTO> countSelect(){
		return dao.CountSelect();	
		}
	


}
