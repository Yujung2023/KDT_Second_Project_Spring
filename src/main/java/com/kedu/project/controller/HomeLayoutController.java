package com.kedu.project.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kedu.project.dto.HomeLayoutDTO;
import com.kedu.project.service.HomeLayoutService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/homeLayout")
public class HomeLayoutController {

    @Autowired
    private HomeLayoutService service;

    // ✅ 저장
    @PostMapping("/save")
    public ResponseEntity<String> saveLayout(
            @RequestBody Map<String, String> body,
            HttpServletRequest request) {
    	System.out.println("여기까지옴");
        String loginId = (String) request.getAttribute("loginID");
        if (loginId == null) return ResponseEntity.badRequest().body(null);

        String layoutJson = body.get("layout");
        System.out.println("layout:"+layoutJson);
        service.saveLayout(loginId, layoutJson);

        return ResponseEntity.ok("saved");
    }

    // ✅ 불러오기
    @GetMapping
    public ResponseEntity<HomeLayoutDTO> getLayout(HttpServletRequest request) {
        String loginId = (String) request.getAttribute("loginID");
        if (loginId == null) return ResponseEntity.badRequest().body(null);
        
        HomeLayoutDTO dto = service.getLayout(loginId);
        System.out.println(dto.getLayout_Json());
        if(dto ==null) {
        	return ResponseEntity.badRequest().body(null);
        }
        
        return ResponseEntity.ok(dto);
    }
}