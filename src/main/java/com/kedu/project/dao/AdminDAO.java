package com.kedu.project.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDAO {
	
	@Autowired
	private SqlSession mybatis;
	
	public List<Map<String, Object>> getAllAdmin() {
	    return mybatis.selectList("Admin.getAllAdmin");
	}
	
	public int addAdmin(String id) {
	    return mybatis.insert("Admin.addAdmin",id);	
	}
	
	public int deleteAdmin(String id){
		return mybatis.delete("Admin.delete",id);
	}
}
