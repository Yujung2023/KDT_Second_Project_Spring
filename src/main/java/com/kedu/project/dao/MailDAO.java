package com.kedu.project.dao;

import java.util.List;

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

	public List<MailDTO> SelectMailList() {
		
		return mybatis.selectList("Mail.SelectMailList");
	}

	public Object deleteMail(List<Long> seqList) {
		
		return mybatis.delete("Mail.deleteMail", seqList);
	}
	
	
	
}

