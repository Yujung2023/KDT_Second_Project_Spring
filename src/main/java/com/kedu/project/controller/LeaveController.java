package com.kedu.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.LeaveRequestPayload;
import com.kedu.project.dto.MemberDTO;
import com.kedu.project.service.LeaveRequestService;
import com.kedu.project.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/leave")
public class LeaveController {
	
	
	@Autowired
	private LeaveRequestService leaveservice;
	
	@Autowired
	private MemberService memberSerice;

	
	
	@GetMapping("/count")
	public ResponseEntity<Double> getRemainLeave(HttpServletRequest request) {
		String loginid = (String) request.getAttribute("loginID");
	    double cnt = leaveservice.getRemainLeave(loginid);
	    return ResponseEntity.ok(cnt);
	}
	
	
	@PostMapping("/request")
	public ResponseEntity<String> requestLeave(@RequestBody LeaveRequestPayload payload, HttpServletRequest request){

	    String loginid = (String) request.getAttribute("loginID");

	    
	    String rank = memberSerice.selectMemberById(loginid).getRank_code();
	    System.out.println("컨트롤러 rank: " + rank);

	    
	    leaveservice.insertLeaveRequest(payload, loginid, rank);

	    return ResponseEntity.ok("휴가 신청 완료");
	}
	
	@GetMapping("/status")
	
	public ResponseEntity<?> getLeaveStatus(HttpServletRequest request){
	
	String loginid=(String)request.getAttribute("loginID");
	
	  MemberDTO member = memberSerice.selectMemberById(loginid);
	    String rankCode = member.getRank_code();   
	    String deptCode = member.getDept_code();   
	    String memberId = member.getId(); 
	    
	    return ResponseEntity.ok(
	    		leaveservice.getLeaveStatus(rankCode, memberId, deptCode));

	
	
	}	

}
