package com.kedu.project.controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.security.JwtUtil;
import com.kedu.project.service.AttendanceService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

	
	@Autowired
	private AttendanceService attendanceService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@GetMapping("/count")
	public List<Map<String, Object>> CountSelect(HttpServletRequest request) {
	    String member_id = jwtUtil.extractUserId(request);
	    return attendanceService.countSelect(member_id);
	}
	
	@PostMapping("checkin")
	public ResponseEntity<String> checkIn(HttpServletRequest request){
		String member_id=jwtUtil.extractUserId(request);
		attendanceService.CheckIn(member_id);
		return ResponseEntity.ok("출근 처리 완료");
	}
	
	@PostMapping("checkout")
	public ResponseEntity<String> checkout(HttpServletRequest reqeust){
		String member_id=jwtUtil.extractUserId(reqeust);
		attendanceService.CheckOut(member_id);
		return ResponseEntity.ok("퇴근 처리 완료");
	}
	
	@GetMapping("today")
	public Map<String, Object> getToday(HttpServletRequest request){
		String member_id=jwtUtil.extractUserId(request);
		return attendanceService.getToday(member_id);
	}



}
