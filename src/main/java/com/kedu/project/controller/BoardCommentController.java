package com.kedu.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.BoardCommentDTO;
import com.kedu.project.dto.BoardDTO;
import com.kedu.project.service.BoardCommentService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comment")
public class BoardCommentController {

	@Autowired
	BoardCommentService boardService;
	
	// insert
	@PostMapping 
	public ResponseEntity<Integer> writeComment(@RequestBody BoardCommentDTO boardCommentDTO , HttpServletRequest request){
		
		String loginId = (String) request.getAttribute("loginID");
		
		boardCommentDTO.setWriter_id(loginId);
		
		boardService.writeComment(boardCommentDTO);
		
		return ResponseEntity.ok(boardCommentDTO.getSeq());
	}
	
	// list
	@GetMapping("/{parent_seq}")
	public ResponseEntity<List<BoardCommentDTO>> getComments(@PathVariable int parent_seq) {
	    List<BoardCommentDTO> comments = boardService.getComments(parent_seq);
	    return ResponseEntity.ok(comments);
	}
	
	// delete
	@DeleteMapping("/{seq}")
	public ResponseEntity<Void> deleteComment (@PathVariable int seq , HttpServletRequest request) {
		
		String loginId = (String) request.getAttribute("loginID");
		
	    boardService.deleteComment(seq);
	    return ResponseEntity.ok().build();
	}
}
