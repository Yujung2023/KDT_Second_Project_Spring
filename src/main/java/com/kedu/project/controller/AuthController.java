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
import com.kedu.project.dto.LoginLogDTO;
import com.kedu.project.dto.MemberDTO;
import com.kedu.project.security.JwtUtil;
import com.kedu.project.service.AuthService;
import com.kedu.project.service.LoginLogService;
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

	@Autowired
	private LoginLogService loginLogService;

	@PostMapping

	public ResponseEntity<?> login(@RequestBody AuthDTO authDTO, HttpServletRequest request){
		System.out.println("id: " + authDTO.getId() + ": pw: " + authDTO.getPw());

		//기본 접속 정보 추출
		String ip = request.getRemoteAddr();
		String userAgent = request.getHeader("User-Agent");

		//세부 정보 분석
		String os = detectOS(userAgent);
		String browser = detectBrowser(userAgent);
		String channel = detectChannel(userAgent);
		
		System.out.println("ip:"+ip);
		System.out.println("os:"+os);
		System.out.println("browser"+browser);
		System.out.println("channel:"+channel);
		//로그인 검증
		MemberDTO member = memberService.findById(authDTO.getId());
		String name = (member != null) ? member.getName() : "누구세요";

		//String state = (member != null && member.getPassword().equals(Encrypt.encrypt(authDTO.getPw())))
		//		? "로그인 성공" : "로그인 실패";
		
//		String state = (member != null && member.getPassword().equals(authDTO.getPw()))
//				? "로그인 성공" : "로그인 실패";
		
		String state = (member != null)	? "로그인 성공" : "로그인 실패";

		LoginLogDTO loginLogDTO = new LoginLogDTO();
		
		loginLogDTO.setUser_id(authDTO.getId());
		loginLogDTO.setState(state);
		loginLogDTO.setIp_address(ip);
		loginLogDTO.setOs(os);
		loginLogDTO.setBrowser(browser);
		loginLogDTO.setChannel(channel);
		
		
		//로그 저장
	    loginLogService.saveLoginLog(loginLogDTO);

		// 토큰 생성 (성공 시에만)
		if ("로그인 성공".equals(state)) {
			String token = jwt.createToken(authDTO.getId(), name);
		   
			Map<String, Object> response = new HashMap<>();
	        response.put("token", token);    //  토큰 포함
	        response.put("member", member);  //  사용자 정보 포함

	        return ResponseEntity.ok(response);
	        
		} else {
			//로그인 실패인데 일단 테스트 편하게 하기 위해서 작성해둠.
			String token = jwt.createToken(authDTO.getId(), name);
			return ResponseEntity.ok(token);
			//return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
		}

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


	//os와 브라우저, 모바일인지 웹인지 확인하는 메서드
	private String detectOS(String userAgent) {
		if (userAgent == null) return "알 수 없음";
		String ua = userAgent.toLowerCase();
		if (ua.contains("windows nt 10")) return "Windows 10";
		if (ua.contains("windows nt 11")) return "Windows 11";
		if (ua.contains("mac os x")) return "macOS";
		if (ua.contains("android")) return "Android";
		if (ua.contains("iphone") || ua.contains("ipad")) return "iOS";
		return "기타";
	}

	private String detectBrowser(String userAgent) {
		if (userAgent == null) return "알 수 없음";
		String ua = userAgent.toLowerCase();
		
		if (ua.contains("samsungbrowser")) return "Samsung Internet";
		if (ua.contains("edg")) return "Edge";
		if (ua.contains("chrome") && !ua.contains("edg")) return "Chrome";
		if (ua.contains("safari") && !ua.contains("chrome")) return "Safari";
		if (ua.contains("firefox")) return "Firefox";
		if (ua.contains("trident") || ua.contains("msie")) return "Internet Explorer";
		return "기타";
	}

	private String detectChannel(String userAgent) {
		if (userAgent == null) return "알 수 없음";
		String ua = userAgent.toLowerCase();
		if (ua.contains("mobile") || ua.contains("android") || ua.contains("iphone")) {
			return "모바일";
		}
		return "웹";
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
