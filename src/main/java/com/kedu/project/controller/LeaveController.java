package com.kedu.project.controller;

import java.security.Principal;
import java.util.Map;

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
    private LeaveRequestService leaveService;

    @Autowired
    private MemberService memberService;

    // ✅ 잔여 연차 조회
    @GetMapping("/count")
    public ResponseEntity<Double> getRemainLeave(HttpServletRequest request) {
        String loginId = (String) request.getAttribute("loginID");
        double cnt = leaveService.getRemainLeave(loginId); // ✅ 이걸로 수정
        return ResponseEntity.ok(cnt);
    }

    // ✅ 휴가 신청
    @PostMapping("/request")
    public ResponseEntity<String> requestLeave(
            @RequestBody LeaveRequestPayload payload,
            HttpServletRequest request) {
    	 System.out.println(">>> RECEIVED REFERENCES: " + payload.getReferences()); // ✅ 이 줄 추가
    	 
        String loginId = (String) request.getAttribute("loginID");
        MemberDTO member = memberService.selectMemberById(loginId);

        if (member == null) {
            return ResponseEntity.badRequest().body("회원 정보를 찾을 수 없습니다.");
        }

        String rank = member.getRank_code();
        System.out.println("컨트롤러 rank: " + rank);

        try { // ★ 여기 추가
            leaveService.insertLeaveRequest(payload, loginId, rank);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("휴가 신청 및 결재 등록 완료");
    }
    // ✅ 휴가 현황 조회
    @GetMapping("/status")
    public ResponseEntity<?> getLeaveStatus(HttpServletRequest request) {
        String loginId = (String) request.getAttribute("loginID");
        MemberDTO member = memberService.selectMemberById(loginId);

        if (member == null) {
            return ResponseEntity.badRequest().body("회원 정보를 찾을 수 없습니다.");
        }

        return ResponseEntity.ok(
                leaveService.getLeaveStatus(
                        member.getRank_code(),
                        member.getId(),
                        member.getDept_code()
                )
        );
    }
    
    @PostMapping("/approve")
    public ResponseEntity<?> approve(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        int seq = (int) data.get("seq");
        String loginId = (String) request.getAttribute("loginID");
        leaveService.approveLeave(seq, loginId);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/reject")
    public ResponseEntity<?> reject(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        int seq = (int) data.get("seq");
        String reason = (String) data.get("reason");
        String loginId = (String) request.getAttribute("loginID"); 
        leaveService.rejectLeave(seq, loginId, reason);
        return ResponseEntity.ok().build();
    }
    
    
    
}
