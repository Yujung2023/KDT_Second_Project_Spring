package com.kedu.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.BoardCommentDTO;
import com.kedu.project.dto.BoardDTO;
import com.kedu.project.service.BoardCommentService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comment")
public class BoardCommentController {

	@Autowired
	BoardCommentService boardCommentService;
	
	// insert
	@PostMapping 
	public ResponseEntity<Integer> writeComment(@RequestBody BoardCommentDTO boardCommentDTO , HttpServletRequest request){
		
		String loginId = (String) request.getAttribute("loginID");
		
		boardCommentDTO.setWriter_id(loginId);
		
		boardCommentService.writeComment(boardCommentDTO);
		
		return ResponseEntity.ok(boardCommentDTO.getSeq());
	}
	
	// list
	@GetMapping("/{parentSeq}")
	public ResponseEntity<Map<String, Object>> getComments(
	    @PathVariable int parentSeq,
	    @RequestParam(defaultValue = "1") int page,
	    @RequestParam(defaultValue = "10") int size
	) {
	    Map<String, Object> result = boardCommentService.getcomments(parentSeq, page, size);
	    return ResponseEntity.ok(result);
	}
	// delete
	@DeleteMapping("/{seq}")
	public ResponseEntity<Void> deleteComment(@PathVariable int seq, HttpServletRequest request) {
	    String loginId = (String) request.getAttribute("loginID");

	    // 댓글의 작성자 ID 조회
	    String writerId = boardCommentService.findWriterIdByCommentSeq(seq);

	    if (writerId == null) {
	        return ResponseEntity.notFound().build(); // 없는 댓글
	    }

	    // 로그인 사용자와 작성자가 다르면 삭제 금지
	    if (!loginId.equals(writerId)) {
	        return ResponseEntity.status(403).build(); // 403 Forbidden
	    }

	    // 동일인 확인 후 삭제 진행
	    boardCommentService.deleteComment(seq);
	    return ResponseEntity.ok().build();
	}
}
