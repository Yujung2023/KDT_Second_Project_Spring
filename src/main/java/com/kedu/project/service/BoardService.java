package com.kedu.project.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.BoardDAO;
import com.kedu.project.dto.BoardDTO;

@Service
public class BoardService {


	 @Autowired
	    private BoardDAO boardDAO;

	    public int writeBoard(BoardDTO dto) {
	        return boardDAO.writeBoard(dto);
	    }

	    public List<BoardDTO> getBoardsByCategory(int categoryId) {
	        return boardDAO.getBoardsByCategory(categoryId);
	    }

	    public BoardDTO getDetail(int seq) {
	        boardDAO.increaseHit(seq); 
	        return boardDAO.getDetail(seq);
	    }

	    public boolean modifyBoard(int seq, BoardDTO dto, String loginId) {
	        BoardDTO existing = boardDAO.getDetail(seq);
	        if (existing == null || !existing.getWriter_id().equals(loginId)) return false;
	        dto.setSeq(seq);
	        boardDAO.modifyBoard(dto);
	        return true;
	    }

	    public boolean deleteBoard(int seq, String loginId) {
	        BoardDTO existing = boardDAO.getDetail(seq);
	        if (existing == null || !existing.getWriter_id().equals(loginId)) return false;
	        boardDAO.deleteBoard(seq);
	        return true;
	    }
	    
	    public void increaseHit(int seq) {
	    	boardDAO.increaseHit(seq);
	    }
}
