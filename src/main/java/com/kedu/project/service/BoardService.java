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
	
	public List<BoardDTO> getBoardsByCategory(int category_id) {
	    return boardDAO.getBoardsByCategory(category_id);
	}
}
