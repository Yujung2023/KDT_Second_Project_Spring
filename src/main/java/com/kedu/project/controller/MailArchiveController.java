package com.kedu.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.MailArchiveDTO;
import com.kedu.project.service.MailArchiveService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/mailArchive")
public class MailArchiveController {
	@Autowired
	 private MailArchiveService mailArchiveService;

		@GetMapping("/history")
		public ResponseEntity<List<MailArchiveDTO>> searchArchiveLog(
	            @RequestParam(required = false) String worker,
	            @RequestParam(required = false) String period) {
	        List<MailArchiveDTO> result = mailArchiveService.searchArchiveLog(worker,period);
	        return ResponseEntity.ok(result);
	    }
	
	    // 검색 API
	    @GetMapping("/search")
	    public ResponseEntity<List<MailArchiveDTO>> searchMails(
	            @RequestParam(required = false) String target,
	            @RequestParam(required = false) String receiver,
	            @RequestParam(required = false) String keyword,
	            @RequestParam(required = false) String period
	    ) {
	        List<MailArchiveDTO> result = mailArchiveService.searchMails(target, receiver, keyword, period);
	        return ResponseEntity.ok(result);
	    }
	    
	    // 내역 남기기
	    @PostMapping
	    public ResponseEntity<Void> saveSearchLog(@RequestBody MailArchiveDTO mailArchiveDTO, HttpServletRequest request){
	    	String loginId = (String)request.getAttribute("loginID");
	    	mailArchiveDTO.setManagerId(loginId);
	    	String ip = request.getRemoteAddr();
	    	mailArchiveDTO.setManagerIp(ip);
	    	
	    	
	    	mailArchiveService.saveSearchLog(mailArchiveDTO);
	        return ResponseEntity.ok().build();
	    }
	    
	
}
