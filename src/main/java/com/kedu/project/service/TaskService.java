package com.kedu.project.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kedu.project.dao.MemberDAO;
import com.kedu.project.dao.TaskDAO;
import com.kedu.project.dto.MemberDTO;
import com.kedu.project.dto.TaskCommentDTO;
import com.kedu.project.dto.TaskDTO;
import com.kedu.project.dto.TaskGroupDTO;


@Service
public class TaskService {

	@Autowired
	private TaskDAO taskDAO;

	@Autowired
	private MemberDAO memberDAO;

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

	public int deleteMember(int group_seq, String member_id) {
		int result = taskDAO.deleteMember(group_seq, member_id);
		return result;
	}



	@Transactional(rollbackFor = Exception.class)
	public int deleteGroup(int group_seq,String loginId) {
		//일단 로그인 아이디가 관리자인지 확인.
		TaskGroupDTO tgDTO = taskDAO.getGroupBySeq(group_seq);
		if(loginId.equals(tgDTO.getManager_id()))
		{
			int deletedMembers = taskDAO.deleteTaskGroupMembers(group_seq);
			int deletedTasks = taskDAO.deleteTasksByGroup(group_seq);
			int deletedGroup = taskDAO.deleteTaskGroup(group_seq);
			return deletedGroup; // 주로 그룹 삭제 결과 리턴
		}

		else //일반 유저면 나가기 처리.
		{
			int deletedMember = taskDAO.deleteMember(group_seq,loginId);
			return deletedMember;
		}
	}

	public TaskDTO insertTask(TaskDTO task) {
		return taskDAO.insertTask(task);   // 시퀀스 포함된 DTO 반환
	}

	// 업무 상태 업데이트
	public int updateTaskStatus(int seq, String status) {
		return taskDAO.updateStatus(seq, status);
	}

	// 업무 정보 모두 업데이트
	public int updateTask(@RequestBody TaskDTO dto) {
		int result = taskDAO.updateTask(dto);
		return result;
	}

	// 업무 삭제
	public int deleteTask(int seq) {
		return taskDAO.deleteTask(seq);   
	}

	public TaskDTO getTaskBySeq(int seq) {
		return taskDAO.getTaskBySeq(seq);
	}

	public int insertComment(TaskCommentDTO commentDTO, String loginId) {
		commentDTO.setWriter_name(memberDAO.selectMemberById(loginId).getName());
		commentDTO.setWriter_id(loginId);

		return taskDAO.insertComment(commentDTO);
	}


	public List<TaskCommentDTO> getCommentsByTask(int taskSeq) {
		List<TaskCommentDTO> list = taskDAO.getCommentsByTask(taskSeq);
		return list;
	}

	public int deleteComment (int seq) {
		return taskDAO.deleteComment(seq);
	}

	public int updateGroup(TaskGroupDTO dto) {
		System.out.println("dto.seq:"+ dto.getSeq());

		return taskDAO.updateGroup(dto);
	}

}
