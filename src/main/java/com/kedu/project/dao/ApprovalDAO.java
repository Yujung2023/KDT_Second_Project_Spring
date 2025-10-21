package com.kedu.project.dao;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.ApprovalDTO;

@Repository
public class ApprovalDAO {

	@Autowired
	private SqlSession mybatis;
	
	
	
	
	 
	
	public List<ApprovalDTO> selectAll(){
		return mybatis.selectList("approval.selectAll");
	}
	
	
	//상태변경(승인,반려 등)에 따라 나오게하는 동작
	public List<ApprovalDTO> selectByStatus(String status){
		return mybatis.selectList("approval.selectByStatus",status);
	}
	
	//내용 추가
	public int insert(ApprovalDTO dto) {
		return mybatis.insert("approval.insert",dto);
	}
	
	//임시 저장 추가
	
	public int tempinsert(ApprovalDTO dto) {
		return mybatis.insert("approval.tempinsert",dto);
	}
	
	//내용 보여줄거임
	public ApprovalDTO getDetail(int seq) {
		return mybatis.selectOne("approval.getDetail",seq);
	}
	
	
	//작성자가 temp문서를 가지고있는가?
	
	public ApprovalDTO findTempByWriter(String writerId) {
		return mybatis.selectOne("approval.findTempByWriter",writerId);
	}
	
	//임시 저장  내용 업데이트
	public int tempUpdate(ApprovalDTO dto) {
		return mybatis.update("approval.tempUpdate",dto);
	}
	
	
	
	
}
