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
	public int writeBoard(BoardDTO dto) {
		return myBatis.insert("Board.insert", dto);
	}

	// list
	public List<BoardDTO> getBoardsByCategory(int categoryId) {
		return myBatis.selectList("Board.selectByCategory", categoryId);
	}

	// important
	public void toggleImportant(int seq) {
		myBatis.update("Board.toggleImportant", seq);
	}
	public List<BoardDTO> selectImportant() {
		return myBatis.selectList("Board.selectImportant");
	}

	// detail
	public BoardDTO getDetail(int seq) {
		return myBatis.selectOne("Board.getDetail", seq);
	}

	// delete
	public void deleteBoard(int seq) {
		myBatis.delete("Board.deleteBoard", seq);
	}

	// modify
	public void modifyBoard(BoardDTO dto) {
		myBatis.update("Board.modifyBoard", dto);
	}

	// hit 증가
	public void increaseHit(int seq) {
		myBatis.update("Board.hit", seq);
	}

}

