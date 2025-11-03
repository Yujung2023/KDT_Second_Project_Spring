package com.kedu.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.MailDTO;
import com.kedu.project.service.MailService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/mail")
public class MailController {


	@Autowired
	private MailService MServ;

	@PostMapping // 메일 작성 (DB insert)
	public ResponseEntity<String> sendMail(@RequestBody MailDTO dto, HttpServletRequest request) {

		String loginId = (String) request.getAttribute("loginID");
	
		try {
		// dto에 로그인한 아이디 넣기
		dto.setUser_id(loginId);
		dto.setSenderId(loginId);
		
		// 주소록에 있는 발신자 이름 조회
	    String senderName = MServ.getNameById(loginId);
		dto.setSenderName(senderName);
		
		MServ.SendMail(dto);
		  return ResponseEntity.ok(dto.getSeq() + "|" + senderName);
		}catch(Exception e) {
			e.printStackTrace();
			  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수신인,제목,내용을 모두 입력해주세요");
		}
		
		   
	}

	@GetMapping("/recent") // 홈에서 받은 메일 리스트 출력
	public ResponseEntity<List<MailDTO>> SelectRecentrecipientMailList(@RequestParam(required = false) String name ,  HttpServletRequest request) {
		 String loginId = (String) request.getAttribute("loginID");
		List<MailDTO> list;
		if (name != null && !name.isEmpty()) { // 안적으면 검색기능 나오게(수정필요)
			list = MServ.searchName(name,loginId);

		}else {
			list = MServ.SelectRecentrecipientMailList(loginId);
		}
		return ResponseEntity.ok(list);
	}
	
	
	@GetMapping // 받은 메일 리스트 출력
	public ResponseEntity<List<MailDTO>> SelectrecipientMailList(@RequestParam(required = false) String name ,  HttpServletRequest request) {
		 String loginId = (String) request.getAttribute("loginID");
		List<MailDTO> list;
		if (name != null && !name.isEmpty()) { // 안적으면 검색기능 나오게(수정필요)
			list = MServ.searchName(name,loginId);

		}else {
			list = MServ.SelectrecipientMailList(loginId);
		}
		return ResponseEntity.ok(list);
	}

	@GetMapping ("/send")// 보낸 메일 리스트 출력
	public ResponseEntity<List<MailDTO>> SelectSendMailList(@RequestParam(required = false) String name ,  HttpServletRequest request) {
		 String loginId = (String) request.getAttribute("loginID");
		List<MailDTO> list;
		if (name != null && !name.isEmpty()) { // 안적으면 검색기능 나오게(수정필요)
			list = MServ.searchsendName(name,loginId);

		}else {
			list = MServ.SelectSendMailList(loginId);
		}
		return ResponseEntity.ok(list);
	}
	
	@DeleteMapping // 메일 삭제
	public ResponseEntity<Void> deleteMail(@RequestBody Map<String, List<Long>> seqListdata) {
		List<Long> seqList = seqListdata.get("seqList");
		MServ.deleteMail(seqList);
		return ResponseEntity.ok().build();
	}




}

