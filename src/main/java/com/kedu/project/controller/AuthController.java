package com.kedu.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.AuthDTO;
import com.kedu.project.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private JwtUtil jwt;

	@PostMapping
	public ResponseEntity<String> login(@RequestBody AuthDTO dto){
		
		if(true) { // 로그인에 성공했을 경우
			String token = jwt.createToken(dto.getId()); //토큰 생성 위치
			 
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Incorrect");
	}
}

	
//	@Autowired
//	private MemberService memberService;
//
//	@PostMapping
//	public ResponseEntity<String> login(@RequestBody AuthDTO authDTO){
//	    System.out.println("id: " + authDTO.getId() + ": pw: " + authDTO.getPw());
//
//	    MemberDTO member = memberService.findById(authDTO.getId());
//	    String name = (member != null) ? member.getName() : "누구세요"; // null방지용
//
//	    String token = jwt.createToken(authDTO.getId(), name);
//	    return ResponseEntity.ok(token);
//	}
//
//
//
//	@GetMapping
//	public ResponseEntity<String> test(HttpServletRequest request){
//		System.out.println(request.getAttribute("loginID"));
//		
//		return ResponseEntity.ok("인증자 전용 데이터");
//	}


