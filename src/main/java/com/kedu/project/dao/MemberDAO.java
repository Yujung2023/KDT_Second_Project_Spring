package com.kedu.project.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO {

	@Autowired
	private SqlSession mybatis;

	
	public int checkId(String id) {
	    return mybatis.selectOne("Member.checkId", id);
	}
	
	
	
}

