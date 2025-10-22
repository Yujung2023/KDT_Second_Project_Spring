package com.kedu.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.MemberDTO;
import com.kedu.project.security.JwtUtil;
import com.kedu.project.service.MemberService;


@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private JwtUtil jwt;
	
	
	@Autowired
	private MemberService memberService;
	
	//회원가입
	@PostMapping
	public ResponseEntity<String> register(@RequestBody MemberDTO memberDTO){
		System.out.println("id: " + memberDTO.getId() + ": pw: " + memberDTO.getPassword() );
		System.out.println("name: " + memberDTO.getName() + ": EnglishN: " + memberDTO.getEnglishName() );
		System.out.println("employmentType : " + memberDTO.getEmploymentType () + ": Hire_d: " + memberDTO.getHire_date() );
		System.out.println("Dept: " + memberDTO.getDept_code() + ": Rank_: " + memberDTO.getRank_code() );
		System.out.println("Job: " + memberDTO.getJob_code() + ": Personal: " + memberDTO.getPersonalEmail() );
		System.out.println("OfficeP: " + memberDTO.getOfficePhone() + ": Mobile: " + memberDTO.getMobilePhone() );
		System.out.println("BirthD: " + memberDTO.getBirthDate() + ": Cal: " + memberDTO.getCalendarType() );
		System.out.println("Zip_c: " + memberDTO.getZip_code() + ": getAddr: " + memberDTO.getAddress_line1() );
		System.out.println("Address_line2: " + memberDTO.getAddress_line2());
		
		int result = memberService.register(memberDTO);
		
		if (result == 0) {
	        // 400 Bad Request 반환
	        return ResponseEntity.badRequest().body("사용자 등록 실패");
	    }

	    // 성공 시 200 OK 반환
	    return ResponseEntity.ok("사용자 등록 성공");
	}

	@PostMapping("/checkId")
	public int checkId(@RequestBody Map<String,String> param) {
	    String id = param.get("id");
	    return memberService.checkId(id);
	}
	
	@GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
		System.out.println("여기까지옴");
		List<MemberDTO> members = memberService.getAllMembers(); // 서비스에서 리스트 조회
        System.out.println("여기까지옴");
        return ResponseEntity.ok(members); // 200 OK와 함께 리스트 반환
    }
	



}