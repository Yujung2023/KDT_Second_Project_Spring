package com.kedu.project.service;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.FileDAO;
import com.kedu.project.dto.MailFileDTO;

@Service
public class FileService {
	
	@Autowired 
	private FileDAO fdao;
	
	// 메일 첨부파일 업로드
	public Object insertMailFileUpload(List<MailFileDTO> fileList) {
		return fdao.insertMailFileUpload(fileList);
	}
	
	// 메일 첨부파일 다운로드
	public MailFileDTO downloadFileBySeq(Long mailSeq , String sysname) {
		
		Map<String, Object> param = new HashMap<>();
		param.put("mailSeq", mailSeq);
		param.put("sysname", sysname);
		
		return fdao.downloadFileBySeq(param);
	}

	// 메일 리스트 저장
	public List<MailFileDTO> getMailFileList(Long mailSeq) {
		
		return fdao.getMailFileList(mailSeq);
	}
	
	
	
}
