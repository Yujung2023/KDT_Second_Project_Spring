package com.kedu.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.kedu.project.dao.MailArchiveDAO;
import com.kedu.project.dto.MailArchiveDTO;

@Service
public class MailArchiveService {

	@Autowired
	private MailArchiveDAO mailArchiveDAO;

	public List<MailArchiveDTO> searchMails(String target, String receiver, String keyword, String period) {
		return mailArchiveDAO.searchMails(target, receiver, keyword, period);
	}
	
	// 내역 남기기
    public void saveSearchLog(@RequestBody MailArchiveDTO dto){
    	mailArchiveDAO.saveSearchLog(dto);
    }
    
    public List<MailArchiveDTO> searchArchiveLog(String worker, String period){
    	return mailArchiveDAO.searchArchiveLog(worker,period);
    }

    
}