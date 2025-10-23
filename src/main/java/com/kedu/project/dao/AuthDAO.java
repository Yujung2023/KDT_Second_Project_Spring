package com.kedu.project.dao;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuthDAO {

	@Autowired
	private SqlSession mybatis;
	
	public int check(String id){	//admin 존재 확인
		return mybatis.selectOne("Auth.check",id);
	}

}
