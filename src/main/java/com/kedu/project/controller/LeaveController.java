package com.kedu.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.security.JwtUtil;
import com.kedu.project.service.LeaveRequestService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/leave")
public class LeaveController {
	
	
	@Autowired
	private LeaveRequestService leaveservice;
	


	
	
	@GetMapping("/count")
	public ResponseEntity<Integer> getRemainLeave(HttpServletRequest request) {
		String loginid = (String) request.getAttribute("loginID");
	    //String empId = jwtUtil.extractUserId(request); // JWT에서 추출
	    int cnt = leaveservice.getRemainLeave(loginid);
	    return ResponseEntity.ok(cnt);
	}


}
