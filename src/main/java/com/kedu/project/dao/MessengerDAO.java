package com.kedu.project.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.MemberDTO;


@Repository
public class MessengerDAO {

	@Autowired
	private SqlSession mybatis;
	
	
	public List<MemberDTO> getAllMembers() {
		 return mybatis.selectList("Messenger.getAllMembers");

	}

}
