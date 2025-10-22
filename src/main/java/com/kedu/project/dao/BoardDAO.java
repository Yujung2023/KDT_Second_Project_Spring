package com.kedu.project.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.BoardDTO;

@Repository
public class BoardDAO {

	@Autowired
	private SqlSession myBatis;
	
	public int writeBoard (BoardDTO boardDTO) {
		
		int result = myBatis.insert("Board.insert",boardDTO);
		
		return result;
	}
}
