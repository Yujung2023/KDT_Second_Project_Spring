package com.kedu.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.LoginLogDTO;
import com.kedu.project.service.LoginLogService;

@RestController
@RequestMapping("/log")
public class LoginLogController {

    @Autowired
    private LoginLogService loginLogService;

    @GetMapping("/search")
    public ResponseEntity<List<LoginLogDTO>> searchLogs(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String ip) {
    	
    	System.out.println("여기 검색");
    	System.out.println("startDate : " + startDate);
    	System.out.println("endDate : " + endDate);
    	System.out.println("userId : " + userId);
    	System.out.println("ip : " + ip);
        List<LoginLogDTO> result = loginLogService.searchLogs(startDate, endDate, userId, ip);
        return ResponseEntity.ok(result);
    }
    
    
}