package com.kedu.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.ApprovalDocDTO;
import com.kedu.project.dto.MemberDTO;
import com.kedu.project.service.ApprovalDocService;
import com.kedu.project.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Eapproval")
public class ApprovalDocController {

    @Autowired
    private ApprovalDocService approvalDocService;
    
    @Autowired
    private MemberService memberSerivce;

    @GetMapping("/candidates")
    public List<MemberDTO> candidates(HttpServletRequest request){

        String loginId = (String) request.getAttribute("loginID"); 
        if(loginId == null){
            throw new RuntimeException("JWT 토큰 인증 정보가 없습니다.");
        }

        MemberDTO login = memberSerivce.findById(loginId);

        Map<String, Object> param = new HashMap<>();
        param.put("deptCode", login.getDept_code());
        param.put("rankCode", login.getRank_code());
        param.put("memberId", login.getId());

        return approvalDocService.selectApproverCandidates(param);
    }
    
    @GetMapping("/line/{approvalId}")
    public ResponseEntity<?> getApprovalLine(@PathVariable String approvalId) {
        return ResponseEntity.ok(approvalDocService.getApprovalLine(approvalId));
    }
    
    @GetMapping("/reference-list")
    public List<MemberDTO> referenceList(HttpServletRequest request){

        //  로그인 정보 세션에서 가져오기
    	String loginId = (String) request.getAttribute("loginID");
    	MemberDTO login = memberSerivce.findById(loginId); // 여기서 dept_code 포함됨

        // 로그인 정보 없으면 에러 방지
        if (login == null) {
            throw new RuntimeException("로그인 정보가 없습니다.");
        }

        // 파라미터 구성
        Map<String, Object> param = new HashMap<>();
        param.put("memberId", login.getId());
        param.put("deptCode", login.getDept_code());

        // ✅ 서비스 호출
        return approvalDocService.selectReferenceList(param);
    }
    
}
