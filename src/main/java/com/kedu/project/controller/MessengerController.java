package com.kedu.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.MemberDTO;
import com.kedu.project.security.JwtUtil;
import com.kedu.project.service.MessengerService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/messenger")
public class MessengerController {

	@Autowired
	private JwtUtil jwt;
	
	@Autowired
	private MessengerService messengerService;

	
	@GetMapping("/member")
	public ResponseEntity<List<MemberDTO>> getAllMembers(){
		List<MemberDTO> members = messengerService.getAllMembers();
        return ResponseEntity.ok(members);

	}
	
	// 자신의 근무상태 변경
	@PutMapping("/status/self")
	public ResponseEntity<String> updateMyWorkStatus(HttpServletRequest request, @RequestBody Map<String, String> data) {
	    String loginId = (String) request.getAttribute("loginID");
	    String newStatus = data.get("work_status");

	    System.out.println("근무 상태 변경 요청: " + loginId + " → " + newStatus);

	   
	    int result = messengerService.updateWorkStatus(loginId, newStatus);

	    if (result > 0) return ResponseEntity.ok("SUCCESS");
	    return ResponseEntity.badRequest().body("FAILED");
	}
	
}
