package com.kedu.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.BoardDTO;
import com.kedu.project.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService boardService;
	
	@PostMapping
	public ResponseEntity<Integer> writeBoard(@RequestBody BoardDTO boardDTO , HttpServletRequest request){
		
		String loginId = (String) request.getAttribute("loginID");
		
		boardDTO.setWriter_id(loginId);
		
		boardService.writeBoard(boardDTO);
		
		return ResponseEntity.ok(boardDTO.getSeq());
	}
}
