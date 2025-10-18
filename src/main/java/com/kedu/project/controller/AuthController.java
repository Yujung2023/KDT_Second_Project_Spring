package com.kedu.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.AuthDTO;
import com.kedu.project.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private JwtUtil jwt;

	@PostMapping
	public ResponseEntity<String> login(@RequestBody AuthDTO authDTO){
		System.out.println("id: " + authDTO.getId() + ": pw: " + authDTO.getPw() );
		
		if(true) { //로그인에 성공했을 경우  우선 회원가입 만들기 전 까지 다 성공으로 처리
			String token = jwt.createToken(authDTO.getId());
			return ResponseEntity.ok(token);
			//토큰 생성
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Incorrect");
	}

	@GetMapping
	public ResponseEntity<String> test(HttpServletRequest request){
		System.out.println(request.getAttribute("loginID"));
		
		return ResponseEntity.ok("인증자 전용 데이터");
	}

}