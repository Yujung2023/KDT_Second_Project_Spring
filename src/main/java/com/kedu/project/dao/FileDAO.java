package com.kedu.project.dao;


import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.MailFileDTO;


@Repository
public class FileDAO {
	
	@Autowired
	private SqlSession mybatis;
	
	
	//메일별 업로드
	public int insertMailFileUpload(List<MailFileDTO> fileList) {
	    int count = 0;
	    for (MailFileDTO file : fileList) {
	        count += mybatis.insert("Mail.MailFileUpload", file);
	    }
	    return count;
	}


	//메일별 다운로드
	public MailFileDTO downloadFileBySeq(Map<String, Object> param) {
	
		return mybatis.selectOne("Mail.MailFileDownload",param);
	}

	//메일별 리스트 저장
	public List<MailFileDTO> getMailFileList(Long mailSeq) {
	
		return mybatis.selectList("Mail.getMailFileList" , mailSeq);
	}


	
	
	
   
}
