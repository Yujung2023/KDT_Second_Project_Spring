package com.kedu.project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.MemberDAO;
import com.kedu.project.dto.MemberDTO;
import com.kedu.project.utils.Encrypt;

@Service
public class MemberService {

	@Autowired
	private MemberDAO memberDao;

	public int checkId(String id) {
		System.out.println("id:"+id);
		int value = memberDao.checkId(id);
		System.out.println(value);
		return value;
	}

	public int register(MemberDTO memberDTO) throws RuntimeException {
		try {
			memberDTO.setPassword(Encrypt.encrypt(memberDTO.getPassword()));
			memberDTO.setOfficeEmail(memberDTO.getId()+"@infinity.com");
			int result = memberDao.register(memberDTO); 
			return result;
		} catch (DataIntegrityViolationException e) {
			// ORA-00001 같은 unique constraint 에러 잡기
			System.out.println("register 에러");
			return 0;
		}
	}
	
	public int update(MemberDTO memberDTO, boolean delProfile) throws RuntimeException {
		try {
			if(memberDTO.getPassword()!=null && !(memberDTO.getPassword().equals("")))
			{
				memberDTO.setPassword(Encrypt.encrypt(memberDTO.getPassword()));
			}
			
			int result = memberDao.update(memberDTO,delProfile); 
			return result;
		} catch (DataIntegrityViolationException e) {
			// ORA-00001 같은 unique constraint 에러 잡기
			System.out.println("register 에러");
			return 0;
		}
	}


	public List<MemberDTO> getMembers(String status,String dept, String employment, String job, String rank) {

		Map<String , Object> param = new HashMap<>();
		param.put("status", status);
		param.put("dept" , dept);
		param.put("employment" , employment);
		param.put("rank" , rank);
		param.put("job" , job);

		List<MemberDTO> members = memberDao.getMembers(param); // 서비스에서 리스트 조회
		return members; 
	}


	public MemberDTO findById(String id) {
		return memberDao.findById(id); //찾기 
	}
	public MemberDTO selectMemberById(String id) {
		return memberDao.selectMemberById(id);
	}
	
	public int deleteMembers(List<String> ids) {
		return memberDao.deleteMembers(ids);
	}

	public int countMembers() {

		return memberDao.countMembers();
	}

	public int updateEmpType(List<String> ids,String empType) {

		return memberDao.updateEmpType(ids, empType);
	}

	public int updateDept(List<String> ids,String dept) {

		return memberDao.updateDept(ids, dept);
	}

	public int updateRank(List<String> ids,String rank) {

		return memberDao.updateRank(ids, rank);
	}

	public int updateJob(List<String> ids,String job) {

		return memberDao.updateJob(ids, job);
	}

	public int updateStatus(List<String> ids,String status) {

		return memberDao.updateStatus(ids, status);
	}
	
	public List<MemberDTO> getListMember() {
	    
	    List<MemberDTO> member = memberDao.getListMember();
	    return member;
	}

}
