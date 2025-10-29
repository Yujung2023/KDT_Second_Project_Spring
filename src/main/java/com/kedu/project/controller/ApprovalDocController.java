package com.kedu.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kedu.project.dto.ApprovalDocDTO;
import com.kedu.project.service.ApprovalDocService;

@RestController
@RequestMapping("/Eapproval")
public class ApprovalDocController {

    @Autowired
    private ApprovalDocService approvalDocService;

    @PostMapping("/createDoc") // ✅ 반드시 PostMapping
    public ResponseEntity<String> createApprovalDoc(@RequestBody ApprovalDocDTO dto) {
        approvalDocService.createApprovalDoc(dto);
        return ResponseEntity.ok("결재 문서 등록 완료");
    }
    
    @GetMapping("/line/{approvalId}")
    public ResponseEntity<?> getApprovalLine(@PathVariable String approvalId) {
        return ResponseEntity.ok(approvalDocService.getApprovalLine(approvalId));
    }
    
}
