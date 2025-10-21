package com.kedu.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.ApprovalDTO;
import com.kedu.project.service.ApprovalService;

@RestController
@RequestMapping("/Eapproval")
public class ApprovalController {
	
	@Autowired
	private ApprovalService approvalservice;
	
	@GetMapping("/A")
	public ResponseEntity<List<ApprovalDTO>> getAll(){
		System.out.println("일단왔음");
		List<ApprovalDTO> list=approvalservice.selectAll();		
		return ResponseEntity.ok(list);
	}
	
	
	
	@GetMapping("/{status}")
	public ResponseEntity<List<ApprovalDTO>> getByStatus(@PathVariable String status){
		List<ApprovalDTO> list;
		
		if(status.equalsIgnoreCase("A")){
			list=approvalservice.selectAll();
		}else {
			list=approvalservice.selectByStatus(status);
		}
		return ResponseEntity.ok(list);
	}
	
	@PostMapping("/write")
	public ResponseEntity<String> insert(@RequestBody ApprovalDTO dto) {
	    System.out.println(" 결재 등록 요청: " + dto);
	    try {
	        approvalservice.insert(dto);
	        return ResponseEntity.ok("등록 성공");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.internalServerError().body("등록 실패");
	    }
	}
	
	@PostMapping("/tempSave")
	public ResponseEntity<Void> tempSave(@RequestBody ApprovalDTO dto){
		System.out.println("임시저장 할게용");
		dto.setStatus("TEMP");
		approvalservice.saveTemp(dto); //insert or update 자동 처리해줄거야 
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/detail/{seq}")
	public ResponseEntity<ApprovalDTO> detail(@PathVariable("seq") int seq){
		ApprovalDTO dto=approvalservice.getDetail(seq);
		return ResponseEntity.ok(dto);
		
	}
	
	
	//임시저장 데이터 불러오기
	@GetMapping("/temp/{writerId}")
	public ResponseEntity<ApprovalDTO> getTemp(@PathVariable String writerId){
		ApprovalDTO dto=approvalservice.getTemp(writerId);
		return ResponseEntity.ok(dto);
	}
	
	
}