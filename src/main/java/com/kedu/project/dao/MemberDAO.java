package com.kedu.project.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.MemberDTO;

@Repository
public class MemberDAO {

	@Autowired
	private SqlSession mybatis;

	
	public int checkId(String id) {
	    return mybatis.selectOne("Member.checkId", id);
	}
	
	public int register(MemberDTO memberDto) {
		return mybatis.insert("Member.register",memberDto);
	}
	
	
	
}

