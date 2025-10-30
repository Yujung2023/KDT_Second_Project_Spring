package com.kedu.project.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedu.project.dao.TaskDAO;
import com.kedu.project.dto.MemberDTO;
import com.kedu.project.dto.TaskDTO;
import com.kedu.project.dto.TaskGroupDTO;


@Service
public class TaskService {

	@Autowired
	private TaskDAO taskDAO;

	@Transactional // 트랜잭션 적용
	public void addGroup(TaskGroupDTO dto) {
		String manager_id = dto.getManager_id();

		// 그룹 생성
		taskDAO.addGroup(dto);

		// 새로 생성된 group_id 가져오기
		int groupId = taskDAO.getLastInsertedGroupId();

		taskDAO.insertGroupMember(groupId, manager_id,"admin");	
		// 멤버 리스트 반복 insert
		for (String memberId : dto.getMembers()) {
			if(memberId.equals(manager_id)) {
				continue;
			}

			taskDAO.insertGroupMember(groupId, memberId,"member");		
		}
	}

	public List<Map<String, Object>> getGroups(String loginId) { 
		List<Map<String, Object>> taskDTOs = taskDAO.getGroups(loginId);
		return taskDTOs;
	}

	public int getMemberCount(int seq) {
		return taskDAO.getMemberCount(seq);
	}

	public TaskGroupDTO getGroupBySeq(int seq) {
		TaskGroupDTO group = taskDAO.getGroupBySeq(seq);
		return group;
	}

	public int getGroupMembers(int seq) {

		int members = taskDAO.getMemberCount(seq);
		return members;

	}
	
	public List<MemberDTO> getMembersByGroup(int seq){
		List<MemberDTO> members = taskDAO.getMembersByGroup(seq);
		return members;
	}
	
	public List<TaskDTO> getTasksByGroup(int groupSeq){
		List<TaskDTO> tasks = taskDAO.getTasksByGroup(groupSeq);
		return tasks;
	}
	
	public int addMember(Map<String, Object> req){
		int groupSeq = Integer.parseInt(req.get("group_seq").toString());
	    String memberId = req.get("member_id").toString();
	    
		int result = taskDAO.insertGroupMember(groupSeq, memberId,"member");
		return result;
	}
}
