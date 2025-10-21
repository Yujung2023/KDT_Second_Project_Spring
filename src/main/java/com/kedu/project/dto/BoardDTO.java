package com.kedu.project.dto;

import java.sql.Timestamp;

public class BoardDTO {

	private int boardId;      // PK
	private int categoryId;   // FK: 게시판
	private String title;      // 제목
	private String content;    // 본문
	private String writerId;   // FK: 작성자
	private String noticeYn;   // 공지 여부
	private int hit;           // 조회수
	private Timestamp createdAt; // 작성일시
	private Timestamp updatedAt; // 수정일시


	public BoardDTO() {}
	
	public BoardDTO(int boardId, int categoryId, String title, String content, String writerId, String noticeYn,
			int hit, Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.boardId = boardId;
		this.categoryId = categoryId;
		this.title = title;
		this.content = content;
		this.writerId = writerId;
		this.noticeYn = noticeYn;
		this.hit = hit;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}

	public String getNoticeYn() {
		return noticeYn;
	}

	public void setNoticeYn(String noticeYn) {
		this.noticeYn = noticeYn;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

}
