package com.kedu.project.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.MemberDTO;
import com.kedu.project.dto.TaskCommentDTO;
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
    public ResponseEntity<Map<String, Object>> getGroupDetail(@PathVariable int groupSeq, HttpServletRequest request) {
    	String loginId = (String) request.getAttribute("loginID");
    	
        Map<String, Object> result = new HashMap<>();
        TaskGroupDTO group = taskService.getGroupBySeq(groupSeq);
        List<TaskDTO> tasks = taskService.getTasksByGroup(groupSeq);
        int membersCount = taskService.getMemberCount(groupSeq);
        List<MemberDTO> members = taskService.getMembersByGroup(groupSeq);

        result.put("group", group);
        result.put("tasks", tasks);
        result.put("membersCount", membersCount);
        result.put("members", members);
        result.put("loginId", loginId);
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
    
    @DeleteMapping("/delMember")
    public ResponseEntity<String> deleteMemberFromGroup(@RequestBody Map<String, Object> data) {
        int groupSeq = Integer.valueOf(data.get("group_seq").toString());
        String memberId = data.get("member_id").toString();
        taskService.deleteMember(groupSeq, memberId);
        return ResponseEntity.ok("삭제 성공");
    }
    
    @DeleteMapping("/delGroup")
    public ResponseEntity<Integer> deleteGroup(@RequestBody Map<String, Object> payload,HttpServletRequest request) {
    	String loginId = (String) request.getAttribute("loginID");
    	
    	int groupSeq = Integer.parseInt(payload.get("group_seq").toString());
        int result = taskService.deleteGroup(groupSeq,loginId);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/insertTask")
    public ResponseEntity<TaskDTO> insertTask(@RequestBody TaskDTO taskDTO) {
    	TaskDTO result = taskService.insertTask(taskDTO);
    	
        return ResponseEntity.ok(result);
    }
    
    //  업무 상태 업데이트
    @PutMapping("/updateStatus")
    public ResponseEntity<?> updateTaskStatus(@RequestBody TaskDTO dto) {
        try {
            int result = taskService.updateTaskStatus(dto.getSeq(), dto.getStatus());
            if (result > 0) {
            	TaskDTO tasks = taskService.getTaskBySeq(dto.getSeq());
                return ResponseEntity.ok().body(tasks);
            } else {
                return ResponseEntity.badRequest().body("해당 업무를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("업무 상태 업데이트 중 오류 발생");
        }
    }
    
    @PutMapping("/updateTask")
    public ResponseEntity<?> updateTask(@RequestBody TaskDTO dto) {
    	System.out.println("seq: "+dto.getSeq() + "status"+dto.getStatus());
    	
        int result = taskService.updateTask(dto);
        
        if (result > 0) {
        	TaskDTO tasks = taskService.getTaskBySeq(dto.getSeq());
            return ResponseEntity.ok(tasks);
        } else {
            return ResponseEntity.badRequest().body("해당 업무를 찾을 수 없습니다.");
        }
    }
    
    // 업무 삭제
    @DeleteMapping("/deleteTask")
    public ResponseEntity<?> deleteTask(@RequestBody Map<String, Object> data) {
        try {
            int seq = Integer.parseInt(data.get("seq").toString());
            int result = taskService.deleteTask(seq);
            
            return ResponseEntity.ok("삭제 완료");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // 댓글 등록
    @PostMapping("/comment")
    public ResponseEntity<?> addComment(@RequestBody TaskCommentDTO commentDTO, HttpServletRequest request) {
    	String loginId = (String) request.getAttribute("loginID");
    	
        try {
            int result = taskService.insertComment(commentDTO,loginId);
            
            if(result > 0) {
            	return ResponseEntity.ok("success");            	
            }
            else
            {
            	return ResponseEntity.badRequest().body("fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("fail");
        }
    }

    // 특정 업무 댓글 목록 조회
    @GetMapping("/comment/{taskSeq}")
    public ResponseEntity<List<TaskCommentDTO>> getComments(@PathVariable("taskSeq") int taskSeq) {
        List<TaskCommentDTO> list = taskService.getCommentsByTask(taskSeq);
        return ResponseEntity.ok(list);
    }
    
    // 	댓글 삭제
    @DeleteMapping("/comment/{seq}")
    public ResponseEntity<?> deleteComment(@PathVariable("seq") int seq) {
        try {
            int result = taskService.deleteComment(seq);
            
            if(result > 0)
            {
            	return ResponseEntity.ok("success");            	
            }
            return ResponseEntity.badRequest().body("fail");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("fail");
        }
    }
    
    
    // 그룹 업데이트
    @PutMapping("/updateGroup")
    public ResponseEntity<?> updateGroup(@RequestBody TaskGroupDTO dto) {
    	System.out.println("dto.seq:"+ dto.getSeq());
    	
        try {
            int result = taskService.updateGroup(dto);
            return result > 0 
                ? ResponseEntity.ok("success")
                : ResponseEntity.status(400).body("fail");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("error");
        }
    }
}