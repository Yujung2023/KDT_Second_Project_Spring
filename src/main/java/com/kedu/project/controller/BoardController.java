package com.kedu.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.BoardDTO;
import com.kedu.project.service.BoardService;

@RestController
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService boardService;
	
	@PostMapping
	public ResponseEntity<Integer> writeBoard(@RequestBody BoardDTO boardDTO){
		
		boardDTO.setWriterId("testUser2");
		int result = boardService.writeBoard(boardDTO);
		
		return ResponseEntity.ok(result);
	}
}
