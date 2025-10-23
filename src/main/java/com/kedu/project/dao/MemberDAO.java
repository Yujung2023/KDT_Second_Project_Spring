package com.kedu.project.dao;

import java.util.HashMap;
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
	
	public int update(MemberDTO memberDto, boolean delProfile) {
		
		System.out.println("dao에서 받은 memberdto profileimage:"+ memberDto.getProfileImage_servName());
		if(delProfile) {
			//프로필 삭제
			return mybatis.insert("Member.updateMemberDelProfile",memberDto);	
		}
		//프로필 삭제 안하고 기존꺼 넣는 코드
		return mybatis.insert("Member.updateMember",memberDto);
	}
	
	public List<MemberDTO> getMembers(Map<String , Object> param) {
		List<MemberDTO> members = mybatis.selectList("Member.getMembers",param);
		return members; 
	}

	public MemberDTO findById(String id) {
		return mybatis.selectOne("Member.findById", id);
	}

	public MemberDTO selectMemberById(String id) {
		return mybatis.selectOne("Member.selectMemberById", id);
	}
	
	public int deleteMembers(List<String> ids) {
		return mybatis.delete("Member.deleteMembers",ids);
	}
	public int countMembers() {
		return mybatis.selectOne("Member.countMembers");
	}
	
	  public int updateEmpType(List<String> ids, String empType) {
	        Map<String, Object> param = new HashMap<>();
	        param.put("ids", ids);
	        param.put("empType", empType);
	        return mybatis.update("Member.updateEmpType", param);
	    }

	    public int updateDept(List<String> ids, String dept) {
	        Map<String, Object> param = new HashMap<>();
	        param.put("ids", ids);
	        param.put("dept", dept);
	        return mybatis.update("Member.updateDept", param);
	    }

	    public int updateRank(List<String> ids, String rank) {
	        Map<String, Object> param = new HashMap<>();
	        param.put("ids", ids);
	        param.put("rank", rank);
	        return mybatis.update("Member.updateRank", param);
	    }

	    public int updateJob(List<String> ids, String job) {
	        Map<String, Object> param = new HashMap<>();
	        param.put("ids", ids);
	        param.put("job", job);
	        return mybatis.update("Member.updateJob", param);
	    }

	    public int updateStatus(List<String> ids, String status) {
	        Map<String, Object> param = new HashMap<>();
	        param.put("ids", ids);
	        param.put("status", status);
	        return mybatis.update("Member.updateStatus", param);
	    }
}

