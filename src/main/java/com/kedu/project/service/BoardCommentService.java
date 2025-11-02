package com.kedu.project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.BoardCommentDAO;
import com.kedu.project.dto.BoardCommentDTO;

@Service
public class BoardCommentService {

    @Autowired
    BoardCommentDAO boardCommentDAO;

    // 댓글 등록
    public int writeComment(BoardCommentDTO boardCommentDTO) {
        return boardCommentDAO.writeComment(boardCommentDTO);
    }

    // 댓글 페이징 조회
    public Map<String, Object> getcomments(int parentSeq, int page, int size) {
        int offset = (page - 1) * size;
        List<BoardCommentDTO> list = boardCommentDAO.getcomments(parentSeq, offset, size);
        int totalCount = boardCommentDAO.getTotalCount(parentSeq);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("totalCount", totalCount);
        return result;
    }

    // 댓글 삭제
    public void deleteComment(int seq) {
        boardCommentDAO.deleteComment(seq);
    }

    // 댓글 작성자 조회
    public String findWriterIdByCommentSeq(int seq) {
        return boardCommentDAO.findWriterIdByCommentSeq(seq);
    }
}
