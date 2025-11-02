package com.kedu.project.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.kedu.project.dto.MemberDTO;
import com.kedu.project.dto.TaskCommentDTO;
import com.kedu.project.dto.TaskDTO;
import com.kedu.project.dto.TaskGroupDTO;

@Repository
public class TaskDAO {

	@Autowired
	private SqlSession mybatis;

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

	public int deleteMember(int group_seq, String member_id) {
		Map<String, Object> params = new HashMap<>();
		params.put("group_seq", group_seq);
		params.put("member_id", member_id);

		return mybatis.delete("Task.deleteMember", params);
	}

	// 그룹 멤버 삭제
	public int deleteTaskGroupMembers(int group_seq) {
		return mybatis.delete("Task.deleteTaskGroupMembers", group_seq);
	}

	// 그룹 내 업무 삭제
	public int deleteTasksByGroup(int group_seq) {
		return mybatis.delete("Task.deleteTasksByGroup", group_seq);
	}

	// 그룹 자체 삭제
	public int deleteTaskGroup(int group_seq) {
		return mybatis.delete("Task.deleteTaskGroup", group_seq);
	}

	// 업무 넣기
	public TaskDTO insertTask(TaskDTO taskDto) {
		mybatis.insert("Task.insertTask",taskDto);   // 시퀀스 포함된 DTO 반환

		System.out.println("getSeq():"+ taskDto.getSeq());

		int seq = taskDto.getSeq();
		return mybatis.selectOne("Task.selectTaskbySeq",seq);
	}

	// 업무 상태 업데이트
	public int updateStatus(int seq, String status) {
		Map<String, Object> params = new HashMap<>();
		params.put("seq", seq);
		params.put("status", status);

		return mybatis.update("Task.updateStatus", params);
	}

	// 업무 정보 모두 업데이트
	public int updateTask(@RequestBody TaskDTO dto) {

		return mybatis.update("Task.updateTask",dto);
	}

	// 업무 삭제
	public int deleteTask(int seq) {
		return mybatis.delete("Task.deleteTask",seq);
	}
	
	// 시퀀스로 업무 데이터 가져오기
	public TaskDTO getTaskBySeq(int seq) {
		return mybatis.selectOne("Task.selectTaskbySeq",seq);
	}
	
	public int insertComment(TaskCommentDTO commentDTO) {
		return mybatis.insert("Task.insertComment",commentDTO);
	}
	
	public List<TaskCommentDTO> getCommentsByTask(int taskSeq) {
        return mybatis.selectList("Task.getCommentsByTask",taskSeq);
    }
	
	public int deleteComment (int seq) {
		return mybatis.delete("Task.deleteComment",seq);
	}
	
	
	//그룹 업데이트
	public int updateGroup(TaskGroupDTO dto) {
		System.out.println("dto.seq:"+ dto.getSeq());
		return mybatis.delete("Task.updateGroup",dto);
	}
	
	public List<Map<String,Object>> getTasksByAssignee(String loginId)
	{
		return  mybatis.selectList("Task.getTasksByAssignee",loginId);
	}
}
