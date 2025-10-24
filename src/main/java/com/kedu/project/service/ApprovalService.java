package com.kedu.project.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.ApprovalDAO;
import com.kedu.project.dto.ApprovalDTO;
import com.kedu.project.dto.MemberDTO;

@Service
public class ApprovalService {

	@Autowired
	public ApprovalDAO dao;
	
	public List<ApprovalDTO> selectAll(){
		return dao.selectAll();
	}
	
	public List<ApprovalDTO> selectByStatus(String status){
		return dao.selectByStatus(status);
	}
	
	public int insert(ApprovalDTO dto) {
		System.out.println("들어갔어!");
		return dao.insert(dto);
	}
	
	public int tempinsert(ApprovalDTO dto) {
		System.out.println("임시저장");
		return dao.tempinsert(dto);
	}
	
	public ApprovalDTO getDetail(int seq) {
		System.out.println("보여줄게");
		return dao.getDetail(seq);
	}
	
	public void saveTemp(ApprovalDTO dto) {
		ApprovalDTO temp=dao.findTempByWriter(dto.getWriter());
		if(temp==null) {
			dao.tempinsert(dto);
		}else {
			dto.setSeq(temp.getSeq());
			dao.tempUpdate(dto);
		}
	}
	
	public ApprovalDTO getTemp(String writerId) {
		return dao.findTempByWriter(writerId);
	}
	
	public List<MemberDTO> getApprovalCandidates(){
		return dao.selelctApprovalCandidates();
	}
	
	
}