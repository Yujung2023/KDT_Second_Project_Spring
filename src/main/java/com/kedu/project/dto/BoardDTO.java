package com.kedu.project.dto;

import java.sql.Timestamp;

public class BoardDTO {

	private int seq;     
	private int category_id;   // 게시판 카테고리
	private String category_name;
	private String title;      // 제목
	private String content;    // 본문
	private String writer_id;   // FK: 작성자
	private String noticeYn;   // 공지 여부
	private int hit;           // 조회수
	private Timestamp createdAt; // 작성일시
	private Timestamp updatedAt; // 수정일시


	public BoardDTO() {}

	public BoardDTO(int seq, int category_id, String category_name, String title, String content, String writer_id,
			String noticeYn, int hit, Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.seq = seq;
		this.category_id = category_id;
		this.category_name = category_name;
		this.title = title;
		this.content = content;
		this.writer_id = writer_id;
		this.noticeYn = noticeYn;
		this.hit = hit;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
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

	public String getWriter_id() {
		return writer_id;
	}

	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
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
