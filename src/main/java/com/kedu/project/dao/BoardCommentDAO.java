package com.kedu.project.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.BoardCommentDTO;

@Repository
public class BoardCommentDAO {

	@Autowired
	private SqlSession myBatis;

	// 댓글 등록
	public int writeComment(BoardCommentDTO boardCommentDTO) {
		return myBatis.insert("BoardComment.insert", boardCommentDTO);
	}

	// 페이징 댓글 조회
	public List<BoardCommentDTO> getcomments(int parentSeq, int offset, int size) {
		Map<String, Object> params = new HashMap<>();
		params.put("parent_seq", parentSeq);
		params.put("offset", offset);
		params.put("size", size);
		return myBatis.selectList("BoardComment.getcomments", params);
	}

	// 전체 댓글 수
	public int getTotalCount(int parentSeq) {
		return myBatis.selectOne("BoardComment.getTotalCount", parentSeq);
	}

	// 댓글 삭제
	public void deleteComment(int seq) {
		myBatis.delete("BoardComment.deleteComment", seq);
	}

	// 댓글 작성자 조회
	public String findWriterIdByCommentSeq(int seq) {
		return myBatis.selectOne("BoardComment.findWriterIdByCommentSeq", seq);
	}
}
