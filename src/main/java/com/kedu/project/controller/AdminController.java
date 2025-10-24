package com.kedu.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.service.AdminService;
import com.kedu.project.service.MemberService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	MemberService memberService;
	
	
	@GetMapping
	public ResponseEntity< List<Map<String, Object>>> getAllAdmin() {
		System.out.println("겟 올 어드민");
		return ResponseEntity.ok(adminService.getAllAdmin());				
	}
	
	@PostMapping
	public ResponseEntity<String> addAdmin(@RequestBody Map<String, String> body) {
	    String id = body.get("id");
	    System.out.println("아이디 전달 확인: " + id);
	    
	    int checkIdResult = memberService.checkId(id);
	    
	    //아이디가 있는지 확인.
	    if(checkIdResult == 0) {
	    	return ResponseEntity.badRequest().body("존재하지 않는 ID 입니다.");
		    
	    }
	    //이미 관리지인지 확인.
	    
	    //관리자 추가.
	    int result = adminService.addAdmin(id);
	    
	    if(result > 0)
	    {
		    return ResponseEntity.ok("관리자 추가 성공");	
	    }
	    return ResponseEntity.badRequest().body("관리자 추가 실패");
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteAdmin(@RequestBody Map<String, String> body) {
	    String id = body.get("id");
	    System.out.println("삭제 요청된 ID: " + id);
	    
	    int result = adminService.deleteAdmin(id);
	    
	    if (result > 0) {
	    	return ResponseEntity.ok("삭제 성공");	    	
	    }
	    return ResponseEntity.badRequest().body("관리자 삭제 실패");
	}

}
