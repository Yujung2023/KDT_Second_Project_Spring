package com.kedu.project.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.TaskDTO;

@Repository
public class TaskDAO {

	@Autowired
	private SqlSession mybatis;


	public int updateJob(List<String> ids, String job) {
		Map<String, Object> param = new HashMap<>();
		param.put("ids", ids);
		param.put("job", job);
		return mybatis.update("Member.updateJob", param);
	}


	// 그룹 생성
	public void addGroup(TaskDTO taskDTO) {
		mybatis.insert("Task.addGroup",taskDTO);
	}

	// 그룹 생성
	public int getLastInsertedGroupId() {
		return mybatis.selectOne("Task.getLastInsertedGroupId");
	}
	
	public void insertGroupMember(int group_seq, String memberId,String role)
	{
		 Map<String,Object> param = new java.util.HashMap<String, Object>();
	        param.put("group_seq", group_seq);
	        param.put("memberId", memberId);
	        param.put("role", role);
	        mybatis.insert("Task.insertGroupMember", param);
	        
	}
}
