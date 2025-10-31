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
	private ScheduleService scheduleService;

	// 일정 등록
	@PostMapping
	public ResponseEntity<Integer> insertSchedule(@RequestBody ScheduleDTO scheduleDTO, HttpServletRequest request) {
		String loginId = (String) request.getAttribute("loginID");
		scheduleDTO.setCreated_id(loginId);
		int seq = scheduleService.insertSchedule(scheduleDTO);
		return ResponseEntity.ok(seq);
	}

	// 카테고리별 조회
	@GetMapping("/category/{category}")
	public ResponseEntity<List<ScheduleDTO>> getSchedulesByCategory(@PathVariable String category, HttpServletRequest request) {
		String loginId = (String) request.getAttribute("loginID");
		List<ScheduleDTO> schedules = scheduleService.getSchedulesByCategory(category, loginId);
		return ResponseEntity.ok(schedules);
	}

	// 전체 일정 조회
	@GetMapping("/all")
	public ResponseEntity<List<ScheduleDTO>> getAllSchedules(HttpServletRequest request) {
		String loginId = (String) request.getAttribute("loginID");
		List<ScheduleDTO> schedules = scheduleService.getAllSchedules(loginId);
		return ResponseEntity.ok(schedules);
	}

	// 일정 상세
	@GetMapping("/detail/{seq}")
	public ResponseEntity<ScheduleDTO> getDetail(@PathVariable int seq) {
		ScheduleDTO scheduleDTO = scheduleService.getDetail(seq);
		if (scheduleDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(scheduleDTO);
	}

	// 일정 수정
	@PutMapping("/{seq}")
	public ResponseEntity<Void> modifySchedule(@PathVariable int seq, @RequestBody ScheduleDTO scheduleDTO, HttpServletRequest request) {
		String loginId = (String) request.getAttribute("loginID");
		ScheduleDTO existing = scheduleService.getDetail(seq);

		if (existing == null) return ResponseEntity.notFound().build();
		if (!loginId.equals(existing.getCreated_id())) return ResponseEntity.status(403).build();

		scheduleService.modifySchedule(seq, scheduleDTO);
		return ResponseEntity.ok().build();
	}

	// 일정 삭제
	@DeleteMapping("/{seq}")
	public ResponseEntity<Void> deleteSchedule(@PathVariable int seq, HttpServletRequest request) {
		String loginId = (String) request.getAttribute("loginID");
		ScheduleDTO existing = scheduleService.getDetail(seq);

		if (existing == null) return ResponseEntity.notFound().build();
		if (!loginId.equals(existing.getCreated_id())) return ResponseEntity.status(403).build();

		scheduleService.deleteSchedule(seq);
		return ResponseEntity.ok().build();
	}
}
