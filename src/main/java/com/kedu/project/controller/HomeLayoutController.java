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

        // ✅ null 먼저 확인해야 함
        if (dto == null) {
            System.out.println("❗ 레이아웃 정보 없음 for loginId = " + loginId);
            return ResponseEntity.ok(null); // or 기본 레이아웃 JSON 리턴
        }

        System.out.println("layout json: " + dto.getLayout_Json());
        return ResponseEntity.ok(dto);
    }
}