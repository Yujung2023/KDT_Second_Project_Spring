package com.kedu.project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.MailDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/mailArchive")
public class MailArchiveController {


//	 private final MailArchiveService mailArchiveService;
//
//	    // 검색 API
//	    @GetMapping("/search")
//	    public ResponseEntity<List<MailArchiveDTO>> searchMails(
//	            @RequestParam(required = false) String target,
//	            @RequestParam(required = false) String receiver,
//	            @RequestParam(required = false) String keyword,
//	            @RequestParam(required = false) String period
//	    ) {
//	        List<MailArchiveDTO> result = mailService.searchMails(target, receiver, keyword, period);
//	        return ResponseEntity.ok(result);
//	    }
	
}
