package com.kedu.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.kedu.project.dto.MailDTO;
import com.kedu.project.service.MailService;

@RestController
@RequestMapping("/mail")
public class MailController {


	@Autowired
	private MailService MServ;
	
	@PostMapping // 메일 작성 (DB insert)
	public ResponseEntity<Void> sendMail(@RequestBody MailDTO dto) {

		MServ.SendMail(dto);

		return ResponseEntity.ok().build();
	}
	
	
	@GetMapping // 메일 리스트 출력
	public ResponseEntity<List<MailDTO>> SelectMailList() {
		
		List<MailDTO> list = MServ.SelectMailList();
		
		return ResponseEntity.ok(list);
	}
	
	@DeleteMapping // 메일 삭제
	public ResponseEntity<Void> deleteMail(@RequestBody Map<String, List<Long>> seqListdata) {
		List<Long> seqList = seqListdata.get("seqList");
		MServ.deleteMail(seqList);
		return ResponseEntity.ok().build();
	}
	
	
	
}

