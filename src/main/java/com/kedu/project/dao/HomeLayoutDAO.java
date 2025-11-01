package com.kedu.project.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.HomeLayoutDTO;

@Repository
public class HomeLayoutDAO {

	@Autowired
	private SqlSession mybatis;


	// ✅ 레이아웃 존재 여부 확인
	public boolean existsByUserId(String userId) {
		Integer count = mybatis.selectOne("HomeLayout.existsByUserId", userId);
		return count != null && count > 0;
	}

	// ✅ 신규 저장
	public void insertLayout(HomeLayoutDTO dto) {
		System.out.println("여기까지옴");
		mybatis.insert("HomeLayout.insertLayout", dto);
	}

	// ✅ 수정
	public void updateLayout(HomeLayoutDTO dto) {
		mybatis.update("HomeLayout.updateLayout", dto);
	}

	// ✅ 조회
	public HomeLayoutDTO getLayoutByUser(String userId) {
		return mybatis.selectOne("HomeLayout.getLayoutByUser", userId);
	}
}