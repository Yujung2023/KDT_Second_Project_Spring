package com.kedu.project.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.MailDTO;

@Repository
public class MailDAO {

	@Autowired
	private SqlSession mybatis;

	public Object SendMail(MailDTO dto) {
		
		return mybatis.insert("Mail.SendMail",dto);
	}
	public String getNameById(String userId) {
		return mybatis.selectOne("Mail.getNameById", userId);
	}
	
	public List<MailDTO> SelectrecipientMailList(String loginId) {
		
		return mybatis.selectList("Mail.SelectrecipientMailList" , loginId);
	}
	
	public List<MailDTO> SelectRecentrecipientMailList(String loginId) {
		
		return mybatis.selectList("Mail.SelectRecentrecipientMailList" , loginId);
	}


	public List<MailDTO> SelectSendMailList(String loginId) {
		
		return mybatis.selectList("Mail.SelectSendMailList" , loginId);
	}

	public Object deleteMail(List<Long> seqList) {
		
		return mybatis.delete("Mail.deleteMail", seqList);
	}

	public List<MailDTO> searchName(Map<String , String> param) {
		
		return mybatis.selectList("Mail.searchName", param);
	}
	
	public List<MailDTO> searchsendName(Map<String , String> param) {
		
		return mybatis.selectList("Mail.searchsendName", param);
	}



	
}

