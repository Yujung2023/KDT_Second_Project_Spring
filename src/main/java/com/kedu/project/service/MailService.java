package com.kedu.project.service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.MailDAO;
import com.kedu.project.dto.MailDTO;

@Service
public class MailService {

	@Autowired
	private MailDAO dao;
	

	public Object SendMail(MailDTO dto) {
		
		return dao.SendMail(dto);
	}
	
	// 로그인 ID로 이름 조회
    public String getNameById(String userId) {
        return dao.getNameById(userId); // DB에서 이름 가져오기
    }

	public List<MailDTO> SelectrecipientMailList(String loginId) {
	    List<MailDTO> mailList = dao.SelectrecipientMailList(loginId);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	    mailList.forEach(mail -> {
	        if(mail.getSendDate() != null) {
	            mail.setSendDateStr(sdf.format(mail.getSendDate()));
	        }
	    });

	    return mailList;  
	}
	
	public List<MailDTO> SelectSendMailList(String loginId) {
	    List<MailDTO> mailList = dao.SelectSendMailList(loginId);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	    mailList.forEach(mail -> {
	        if(mail.getSendDate() != null) {
	            mail.setSendDateStr(sdf.format(mail.getSendDate()));
	        }
	    });

	    return mailList;  
	}
	

	public Object deleteMail(List<Long> seqList) {

		return dao.deleteMail(seqList);
		
	}

	public List<MailDTO> searchName(String name , String loginId) {
		
		Map<String , String> param = new HashMap<>();
		param.put("name", name);
		param.put("loginId", loginId);
		
		
	    List<MailDTO> mailList = dao.searchName(param);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	    mailList.forEach(mail -> {
	        if(mail.getSendDate() != null) {
	            mail.setSendDateStr(sdf.format(mail.getSendDate()));
	        }
	    });

	    return mailList;  // ← 수정: 포맷팅된 mailList 반환
	}
	
	

	public List<MailDTO> searchsendName(String name , String loginId) {
		
		Map<String , String> param = new HashMap<>();
		param.put("name", name);
		param.put("loginId", loginId);
		
		
	    List<MailDTO> mailList = dao.searchsendName(param);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	    mailList.forEach(mail -> {
	        if(mail.getSendDate() != null) {
	            mail.setSendDateStr(sdf.format(mail.getSendDate()));
	        }
	    });

	    return mailList;  // ← 수정: 포맷팅된 mailList 반환
	}




	
	
}
