package com.kedu.project.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.MailArchiveDTO;

@Repository
public class MailArchiveDAO {

	@Autowired
	private SqlSession myBatis;

	  public List<MailArchiveDTO> searchMails(String target, String receiver, String keyword, String period) {
	        Map<String, Object> params = new HashMap<>();
	        params.put("target", target);
	        params.put("receiver", receiver);
	        params.put("keyword", keyword);
	        params.put("period", period);

	        return myBatis.selectList("MailArchiveMapper.searchMails", params);
	    }
}
