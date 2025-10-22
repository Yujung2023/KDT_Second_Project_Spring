package com.kedu.project.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.AttendanceDTO;
import com.kedu.project.service.AttendanceService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

	
	@Autowired
	private AttendanceService attendanceService;
	
	@GetMapping("/count")
	public ResponseEntity<List<AttendanceDTO>> CountSelect(){
		System.out.println("근태 보여줄거야");
		List<AttendanceDTO> list=attendanceService.countSelect();
		return ResponseEntity.ok(list);
	}


}
