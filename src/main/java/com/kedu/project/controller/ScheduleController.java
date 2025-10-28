package com.kedu.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.BoardDTO;
import com.kedu.project.dto.ScheduleDTO;
import com.kedu.project.service.BoardService;
import com.kedu.project.service.ScheduleService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

	@Autowired
	ScheduleService scheduleService;
	
	// insert
	@PostMapping 
	public ResponseEntity<Integer> insertSchedule(@RequestBody ScheduleDTO scheduleDTO , HttpServletRequest request){
		
		String loginId = (String) request.getAttribute("loginID");
		
		scheduleDTO.setCreated_id(loginId);
		
		int seq = scheduleService.insertSchedule(scheduleDTO);
		
		return ResponseEntity.ok(seq);
	}
	
	// 카테고리별 일정 목록 조회
	@GetMapping("/category/{category}")
	public ResponseEntity<List<ScheduleDTO>> getSchedulesByCategory(@PathVariable String category) {
	    List<ScheduleDTO> schedules = scheduleService.getSchedulesByCategory(category);
	    return ResponseEntity.ok(schedules);
	}
	
	// 카테고리 all
	@GetMapping("/all")
	public ResponseEntity<List<ScheduleDTO>> getAllSchedules() {
	    List<ScheduleDTO> schedules = scheduleService.getAllSchedules();
	    return ResponseEntity.ok(schedules);
	}
	
	// detail
	@GetMapping("/detail/{seq}")
	public ResponseEntity<ScheduleDTO> getDetail (@PathVariable int seq) {
		System.out.println("디테일");
		ScheduleDTO scheduleDTO = scheduleService.getDetail(seq);
		return ResponseEntity.ok(scheduleDTO);
	}
	
	// update
	@PutMapping("/{seq}")
	public ResponseEntity<Void> modifySchedule (@PathVariable int seq , @RequestBody ScheduleDTO scheduleDTO , HttpServletRequest request) {
		
		String loginId = (String) request.getAttribute("loginID");
		
		scheduleService.modifySchedule(seq, scheduleDTO);
	    return ResponseEntity.ok().build();
	}
	
	// delete
	@DeleteMapping("/{seq}")
	public ResponseEntity<Void> deleteSchedule (@PathVariable int seq , HttpServletRequest request) {
		
		String loginId = (String) request.getAttribute("loginID");
		
		scheduleService.deleteSchedule(seq);
	    return ResponseEntity.ok().build();
	}


}
