package com.kedu.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kedu.project.dao.MailArchiveDAO;
import com.kedu.project.dto.MailArchiveDTO;

@Service
public class MailArchiveService {

	    private MailArchiveDAO mailArchiveDAO;

	    public List<MailArchiveDTO> searchMails(String target, String receiver, String keyword, String period) {
	        return mailArchiveDAO.searchMails(target, receiver, keyword, period);
	    }
	}