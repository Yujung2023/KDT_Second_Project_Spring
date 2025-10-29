package com.kedu.project.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.AuthDTO;
import com.kedu.project.dto.MemberDTO;
import com.kedu.project.security.JwtUtil;
import com.kedu.project.service.AuthService;
import com.kedu.project.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private JwtUtil jwt;

	@Autowired
	private MemberService memberService;

	@Autowired
	private AuthService authService;

	
	@PostMapping
	public ResponseEntity<?> login(@RequestBody AuthDTO authDTO){
	    System.out.println("id: " + authDTO.getId() + ", pw: " + authDTO.getPw());

	    //  사용자 정보 조회
	    MemberDTO member = memberService.findById(authDTO.getId());
	    if (member == null) {
	        return ResponseEntity.badRequest().body("존재하지 않는 사용자입니다.");
	    }

	    //  JWT 생성
	    String token = jwt.createToken(member.getId(), member.getName());

	    //  token + 사용자 정보 함께 응답
	    Map<String, Object> response = new HashMap<>();
	    response.put("token", token);
	    response.put("member", member);

	    return ResponseEntity.ok(response);
	}


	@GetMapping
	public ResponseEntity<String> test(HttpServletRequest request){
		System.out.println(request.getAttribute("loginID"));
		
		return ResponseEntity.ok("인증자 전용 데이터");
	}
	
	@GetMapping("/check") //권한 체크
	public ResponseEntity<String> check(HttpServletRequest request){
		String loginId = (String)request.getAttribute("loginID");
		System.out.println("로그인 아이디는:"+loginId);
		int result = authService.check(loginId);
		System.out.println("결과는:" + result);
		if(result > 0) {
			System.out.println("관리자 맞음");
			return ResponseEntity.ok("관리자 맞습니다.");	
		}
		System.out.println("관리자 아님");
		return ResponseEntity.badRequest().body("관리자가 아닙니다.");
		
	}
	
	
}

	
//	
//	@PostMapping
//	public ResponseEntity<String> login(@RequestBody AuthDTO dto){
//		
//		if(true) { // 로그인에 성공했을 경우
//			String token = jwt.createToken(dto.getId()); //토큰 생성 위치
//			 
//			return ResponseEntity.ok(token);
//		}
//		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Incorrect");
//	}
//
