package com.kedu.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.BoardDAO;
import com.kedu.project.dto.BoardDTO;

@Service
public class BoardService {

	@Autowired
	BoardDAO boardDAO;

	// insert
    public int writeBoard(BoardDTO boardDTO) {
		return boardDAO.writeBoard(boardDTO);
	}
	
	// list
	public List<BoardDTO> getBoardsByCategory(int category_id) {
	    return boardDAO.getBoardsByCategory(category_id);
	}
	
	// detail
	public BoardDTO getDetail(int seq) {
	    return boardDAO.getDetail(seq);
	}
	
	// delete
	public void deleteBoard (int seq) {
	    boardDAO.deleteBoard(seq);
	}
	
	// modify
	public void modifyBoard (int seq , BoardDTO boardDTO) {
		boardDTO.setSeq(seq);
	    boardDAO.modifyBoard(boardDTO);
	}
}
