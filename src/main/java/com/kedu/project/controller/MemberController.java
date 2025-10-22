package com.kedu.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<List<MemberDTO>> getMembers(
	        @RequestParam(required = false) String status,
	        @RequestParam(required = false) String dept,
	        @RequestParam(required = false) String employment,
	        @RequestParam(required = false) String job,
	        @RequestParam(required = false) String rank
	        ) {
		System.out.println("지금 도착");
		System.out.println("status:" + status);
		System.out.println("dept:" + dept);
		System.out.println("employment:" + employment);
		System.out.println("job:" + job);
		System.out.println("rank:" + rank);
	    List<MemberDTO> members = memberService.getMembers(status, dept, employment,job,rank);
	    
	    for(MemberDTO member : members) {
	    	System.out.println(member.getEmployee_no());
	    }
	    return ResponseEntity.ok(members);
	}
	
	
//	@DeleteMapping
//    public ResponseEntity<String> deleteMembers(@RequestBody List<String> data) {
//        System.out.println("=== 삭제 요청 ===");
//        for (String id : data) {
//            System.out.println("삭제할 사용자 ID: " + id);
//        }
//        // DB 삭제 로직
//        int result = memberService.deleteMembers(data);
//        return ResponseEntity.ok("SUCCESS");
//    }
//        
//    
//    // 근로형태 수정
//    @PutMapping("/empType")
//    public ResponseEntity<String> updateEmpType(@RequestBody Map<String, Object> data) {
//        List<String> ids = (List<String>) data.get("ids");
//        String empType = (String) data.get("empType");
//
//        System.out.println("=== 근로형태 수정 요청 ===");
//        for (String id : ids) {
//            System.out.println("수정할 사용자 ID: " + id);
//        }
//        System.out.println("변경할 근로형태: " + empType);
//
//        // TODO: service.updateEmpType(ids, empType);
//        return ResponseEntity.ok("SUCCESS");
//    }
//
//    //  부서 수정
//    @PutMapping("/dept")
//    public ResponseEntity<String> updateDept(@RequestBody Map<String, Object> data) {
//        List<String> ids = (List<String>) data.get("ids");
//        String dept = (String) data.get("dept");
//
//        System.out.println("=== 부서 수정 요청 ===");
//        for (String id : ids) {
//            System.out.println("수정할 사용자 ID: " + id);
//        }
//        System.out.println("변경할 부서: " + dept);
//
//        // TODO: service.updateDept(ids, dept);
//        return ResponseEntity.ok("SUCCESS");
//    }
//
//    // 직위 수정
//    @PutMapping("/rank")
//    public ResponseEntity<String> updateRank(@RequestBody Map<String, Object> data) {
//        List<String> ids = (List<String>) data.get("ids");
//        String rank = (String) data.get("rank");
//
//        System.out.println("=== 직위 수정 요청 ===");
//        for (String id : ids) {
//            System.out.println("수정할 사용자 ID: " + id);
//        }
//        System.out.println("변경할 직위: " + rank);
//
//        // TODO: service.updateRank(ids, rank);
//        return ResponseEntity.ok("SUCCESS");
//    }
//
//    // 직무 수정
//    @PutMapping("/job")
//    public ResponseEntity<String> updateJob(@RequestBody Map<String, Object> data) {
//        List<String> ids = (List<String>) data.get("ids");
//        String job = (String) data.get("job");
//
//        System.out.println("=== 직무 수정 요청 ===");
//        for (String id : ids) {
//            System.out.println("수정할 사용자 ID: " + id);
//        }
//        System.out.println("변경할 직무: " + job);
//
//        // TODO: service.updateJob(ids, job);
//        return ResponseEntity.ok("SUCCESS");
//    }
//
//    // 재직 상태 수정
//    @PutMapping("/status")
//    public ResponseEntity<String> updateStatus(@RequestBody Map<String, Object> data) {
//        List<String> ids = (List<String>) data.get("ids");
//        String status = (String) data.get("status");
//
//        System.out.println("=== 재직 상태 수정 요청 ===");
//        for (String id : ids) {
//            System.out.println("수정할 사용자 ID: " + id);
//        }
//        System.out.println("변경할 재직 상태: " + status);
//
//        // TODO: service.updateStatus(ids, status);
//        return ResponseEntity.ok("SUCCESS");
//    }


}