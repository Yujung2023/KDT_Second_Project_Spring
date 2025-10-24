package com.kedu.project.service;

import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kedu.project.dao.BoardCommentDAO;
import com.kedu.project.dao.BoardDAO;
import com.kedu.project.dto.BoardCommentDTO;
import com.kedu.project.dto.BoardDTO;

@Service
public class BoardCommentService {
	
	@Autowired
	BoardCommentDAO boardCommentDAO;

	// insert
	public int writeComment(BoardCommentDTO boardCommentDTO) {
		return boardCommentDAO.writeComment(boardCommentDTO);
	}
	
	// list
	public List<BoardCommentDTO> getComments(int parent_seq) {
	    return boardCommentDAO.getComments(parent_seq);
	}

	// delete
	public void deleteComment (int seq) {
		boardCommentDAO.deleteComment(seq);
	}
	
}
