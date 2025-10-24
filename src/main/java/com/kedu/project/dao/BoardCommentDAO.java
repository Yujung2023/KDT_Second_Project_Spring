package com.kedu.project.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.BoardCommentDTO;
import com.kedu.project.dto.BoardDTO;

@Repository
public class BoardCommentDAO {
	
	@Autowired
	private SqlSession myBatis;
	
	// insert
	public int writeComment (BoardCommentDTO boardCommentDTO) {
		
		int result = myBatis.insert ("BoardComment.insert",boardCommentDTO);
		
		return result;
	}
	
	// list
	public List<BoardCommentDTO> getComments(int parent_seq) {
	    return myBatis.selectList ("BoardComment.getComments", parent_seq);
	}

	// delete
	public void deleteComment (int seq) {
	    myBatis.delete ("BoardComment.deleteComment", seq);
	}
}
