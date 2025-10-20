package com.kedu.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.service.LeaveRequestService;

@RestController
@RequestMapping("/leave")
public class LeaveController {
	
	
	@Autowired
	private LeaveRequestService leaveservice;
	
	
	@GetMapping("/count")
	public ResponseEntity<Integer> getRemainLeave() {
        int cnt = leaveservice.getRemainLeave("user1"); // 로그인 후 교체 예정
        return ResponseEntity.ok(cnt);
    }

}
