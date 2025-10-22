package com.kedu.project.dao;

import java.util.List;
import java.util.Map;

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
	
	public List<MemberDTO> getMembers(Map<String , Object> param) {
        List<MemberDTO> members = mybatis.selectList("Member.getMembers",param);
        return members; 
    }
	
	public MemberDTO findById(String id) {
	    return mybatis.selectOne("Member.findById", id);
	}
	
}

