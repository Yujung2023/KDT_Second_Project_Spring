package com.kedu.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.TaskDTO;
import com.kedu.project.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskService taskService;
	
    @PostMapping("/addGroup")
    public ResponseEntity<?> addGroup(@RequestBody TaskDTO taskDto) {
    	
    	System.out.println("manager:" + taskDto.getManager_id());
    	
        try {
        	taskService.addGroup(taskDto);
            return ResponseEntity.ok("그룹 생성 완료");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("그룹 생성 실패");
        }
    }
}