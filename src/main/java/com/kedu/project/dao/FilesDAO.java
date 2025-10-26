package com.kedu.project.dao;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.FilesDTO;
import com.kedu.project.dto.MailFileDTO;


@Repository
public class FilesDAO {

	@Autowired
	private SqlSession mybatis;

	public void uploadFile(FilesDTO filesDTO) {
		mybatis.insert("File.uploadFile", filesDTO);
	}
	
	public FilesDTO downloadFile(String sysname) {
		return mybatis.selectOne("File.downloadFile", sysname);
	}
	public List<FilesDTO> getFilesList(String module_type, int module_seq) {
		Map<String, Object> param = new HashMap<>();
		param.put("module_type", module_type);
		param.put("module_seq", module_seq);
		return mybatis.selectList("File.getFilesList", param);
	}

	public int deleteFile(String sysname) {
		return mybatis.delete("File.deleteFile", sysname);
	}
}

