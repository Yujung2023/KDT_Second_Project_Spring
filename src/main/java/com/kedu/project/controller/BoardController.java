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
import com.kedu.project.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService boardService;
	
	// insert
	@PostMapping 
	public ResponseEntity<Integer> writeBoard(@RequestBody BoardDTO boardDTO , HttpServletRequest request){
		
		String loginId = (String) request.getAttribute("loginID");
		
		boardDTO.setWriter_id(loginId);
		
		boardService.writeBoard(boardDTO);
		
		return ResponseEntity.ok(boardDTO.getSeq());
	}
	
	// list
	@GetMapping("/category/{category_id}")
	public ResponseEntity<List<BoardDTO>> getBoardsByCategory(@PathVariable int category_id) {
	    List<BoardDTO> boards = boardService.getBoardsByCategory(category_id);
	    return ResponseEntity.ok(boards);
	}
	
	// detail
	@GetMapping("/detail/{seq}")
	public ResponseEntity<BoardDTO> getDetail (@PathVariable int seq) {
		System.out.println("디테일");
		BoardDTO boardDTO = boardService.getDetail(seq);
		return ResponseEntity.ok(boardDTO);
	}
	
	// update
	@PutMapping("/{seq}")
	public ResponseEntity<Void> modifyBoard (@PathVariable int seq , @RequestBody BoardDTO boardDTO) {
		boardService.modifyBoard(seq , boardDTO);
		return ResponseEntity.ok().build();
	}
	
	// delete
	@DeleteMapping("/{seq}")
	public ResponseEntity<Void> deleteBoard (@PathVariable int seq) {
		boardService.deleteBoard(seq);
		return ResponseEntity.ok().build();
	}
	
}
