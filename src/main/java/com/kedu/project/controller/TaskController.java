package com.kedu.project.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.MemberDTO;
import com.kedu.project.dto.TaskDTO;
import com.kedu.project.dto.TaskGroupDTO;
import com.kedu.project.service.TaskService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskService taskService;
	
    @PostMapping("/addGroup")
    public ResponseEntity<?> addGroup(@RequestBody TaskGroupDTO taskGroupDTO) {
    	
    	System.out.println("manager:" + taskGroupDTO.getManager_id());
    	
        try {
        	taskService.addGroup(taskGroupDTO);
            return ResponseEntity.ok("그룹 생성 완료");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("그룹 생성 실패");
        }
    }
    
    
    
    @GetMapping("/groups")
    public ResponseEntity<List<Map<String, Object>>> getGroups(HttpServletRequest request) {
        String loginId = (String) request.getAttribute("loginID");

        // MyBatis에서 Map 리스트로 받아오기
        List<Map<String, Object>> groups = taskService.getGroups(loginId);

        // 최종 결과
        List<Map<String, Object>> result = new ArrayList<>();

        // 그룹별 멤버 수 추가
        for (Map<String, Object> group : groups) {
            // group_seq 컬럼명에 맞게 꺼내기 (Oracle은 대문자 주의)
            int groupSeq = ((Number) group.get("SEQ")).intValue();

            int count = taskService.getMemberCount(groupSeq);

            // 기존 데이터 그대로 복사
            Map<String, Object> map = new HashMap<>(group);
            map.put("membersCount", count);

            result.add(map);
        }

        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/detail/{groupSeq}")
    public ResponseEntity<Map<String, Object>> getGroupDetail(@PathVariable int groupSeq) {
        Map<String, Object> result = new HashMap<>();
        TaskGroupDTO group = taskService.getGroupBySeq(groupSeq);
        List<TaskDTO> tasks = taskService.getTasksByGroup(groupSeq);
        int membersCount = taskService.getMemberCount(groupSeq);
        List<MemberDTO> members = taskService.getMembersByGroup(groupSeq);

        result.put("group", group);
        result.put("tasks", tasks);
        result.put("membersCount", membersCount);
        result.put("members", members);

        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/addMember")
    public ResponseEntity<String> addMember(@RequestBody Map<String, Object> req)
    {
        int result = taskService.addMember(req);

        if(result >0)
        {
        	return ResponseEntity.ok("Member added successfully");        	
        }
        return ResponseEntity.ok("fail");  
    	 
    }
}