package com.kedu.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.MemberDTO;
import com.kedu.project.security.JwtUtil;
import com.kedu.project.service.MessengerService;

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
	
}
