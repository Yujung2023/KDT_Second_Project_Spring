package com.kedu.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.GetMapping;
>>>>>>> 29a2a6656e479b23beb52c2383a0387d3cd8d6d8
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.AuthDTO;
<<<<<<< HEAD
import com.kedu.project.security.JwtUtil;
=======
import com.kedu.project.dto.MemberDTO;
import com.kedu.project.security.JwtUtil;
import com.kedu.project.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
>>>>>>> 29a2a6656e479b23beb52c2383a0387d3cd8d6d8

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private JwtUtil jwt;
	
<<<<<<< HEAD
	@PostMapping
	public ResponseEntity<String> login(@RequestBody AuthDTO dto){
		
		if(true) { // 로그인에 성공했을 경우
			String token = jwt.createToken(dto.getId()); //토큰 생성 위치
			 
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Incorrect");
	}
}
=======
	
	@Autowired
	private MemberService memberService;

	@PostMapping
	public ResponseEntity<String> login(@RequestBody AuthDTO authDTO){
	    System.out.println("id: " + authDTO.getId() + ": pw: " + authDTO.getPw());

	    MemberDTO member = memberService.findById(authDTO.getId());
	    String name = (member != null) ? member.getName() : "누구세요"; // null방지용

	    String token = jwt.createToken(authDTO.getId(), name);
	    return ResponseEntity.ok(token);
	}



	@GetMapping
	public ResponseEntity<String> test(HttpServletRequest request){
		System.out.println(request.getAttribute("loginID"));
		
		return ResponseEntity.ok("인증자 전용 데이터");
	}

}
>>>>>>> 29a2a6656e479b23beb52c2383a0387d3cd8d6d8
