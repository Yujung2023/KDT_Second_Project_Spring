package com.kedu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedu.project.dao.TaskDAO;
import com.kedu.project.dto.TaskDTO;


@Service
public class TaskService {

	@Autowired
	private TaskDAO taskDAO;

	@Transactional // 트랜잭션 적용
	public void addGroup(TaskDTO dto) {
		
		String manager_id = dto.getManager_id();
		
		
		
		// 그룹 생성
		taskDAO.addGroup(dto);

		// 새로 생성된 group_id 가져오기
		int groupId = taskDAO.getLastInsertedGroupId();

		// 멤버 리스트 반복 insert
		for (String memberId : dto.getMembers()) {
			if(memberId.equals(manager_id)) {
				taskDAO.insertGroupMember(groupId, memberId,"admin");				
			}
			else
			{
				taskDAO.insertGroupMember(groupId, memberId,"member");		
			}
		}
	}
}
