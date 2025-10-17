package com.kedu.project.service;

import java.text.SimpleDateFormat;
import java.util.List;

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

	public List<MailDTO> SelectMailList() {
	    List<MailDTO> mailList = dao.SelectMailList();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	    mailList.forEach(mail -> {
	        if(mail.getSendDate() != null) {
	            mail.setSendDateStr(sdf.format(mail.getSendDate()));
	        }
	    });

	    return mailList;  // ← 수정: 포맷팅된 mailList 반환
	}

	public Object deleteMail(List<Long> seqList) {

		return dao.deleteMail(seqList);
		
	}

	
	
}
