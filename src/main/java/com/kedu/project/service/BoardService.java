package com.kedu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.BoardDAO;
import com.kedu.project.dto.BoardDTO;

@Service
public class BoardService {

	@Autowired
	BoardDAO boardDAO;
	
	public int writeBoard(BoardDTO boardDTO) {
		
		return boardDAO.writeBoard(boardDTO);
	}
}
