package com.kedu.project.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.MemberDTO;
import com.kedu.project.dto.TaskDTO;
import com.kedu.project.dto.TaskGroupDTO;

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
	public void addGroup(TaskGroupDTO taskDTO) {
		mybatis.insert("Task.addGroup",taskDTO);
	}

	// 그룹 생성
	public int getLastInsertedGroupId() {
		return mybatis.selectOne("Task.getLastInsertedGroupId");
	}
	
	public int insertGroupMember(int group_seq, String memberId,String role)
	{
		 Map<String,Object> param = new java.util.HashMap<String, Object>();
	        param.put("group_seq", group_seq);
	        param.put("memberId", memberId);
	        param.put("role", role);
	        return mybatis.insert("Task.insertGroupMember", param);
	        
	}
	
	public List<Map<String, Object>> getGroups(String loginId) { 
		List<Map<String, Object>> taskDTOs = mybatis.selectList("Task.getGroupsById",loginId);
		return taskDTOs;
	}

	
	public int getMemberCount(int group_seq) {
		 return mybatis.selectOne("Task.getMemberCount",group_seq);
	 }
	
	public TaskGroupDTO getGroupBySeq(int seq) {
		TaskGroupDTO group = mybatis.selectOne("Task.getGroupBySeq",seq);
		return group;
	}
	
	public List<MemberDTO> getMembersByGroup(int group_seq){
		List<MemberDTO> param = mybatis.selectList("Task.getMembersByGroup",group_seq);
		return param;
	}
	
	public List<TaskDTO> getTasksByGroup(int group_seq){
		List<TaskDTO> tasks = mybatis.selectList("Task.getTasksByGroup",group_seq);
		return tasks;
	}
}
