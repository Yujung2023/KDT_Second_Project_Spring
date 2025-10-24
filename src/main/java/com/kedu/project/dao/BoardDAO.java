package com.kedu.project.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.BoardDTO;

@Repository
public class BoardDAO {

	@Autowired
	private SqlSession myBatis;
	
	// insert
	public int writeBoard (BoardDTO boardDTO) {
		
		int result = myBatis.insert ("Board.insert",boardDTO);
		
		return result;
	}
	
	// list
	public List<BoardDTO> getBoardsByCategory(int category_id) {
	    return myBatis.selectList ("Board.selectByCategory", category_id);
	}
	
	// detail
	public BoardDTO getDetail (int seq) {
	    return myBatis.selectOne ("Board.getDetail", seq);
	}
	
	// delete
	public void deleteBoard (int seq) {
	    myBatis.delete ("Board.deleteBoard", seq);
	}
	
	// modify
	public void modifyBoard (BoardDTO boardDTO) {
	    myBatis.update("Board.modifyBoard", boardDTO);
	}
}
