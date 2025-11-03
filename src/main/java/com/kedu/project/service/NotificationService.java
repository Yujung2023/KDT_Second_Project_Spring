package com.kedu.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.kedu.project.dao.NotificationDAO;
import com.kedu.project.dto.NotificationDTO;

@Service
public class NotificationService {
	@Autowired
	private NotificationDAO notificationDao;
	
	
    public List<NotificationDTO> getNotificationByLoginId(String loginId) {
        List<NotificationDTO> list = notificationDao.getNotificationByLoginId(loginId);
        return list;
    }
    
    
    public void insertNotice(NotificationDTO notice) {
    	System.out.println("리시버아디:"+notice.getReceiver_id());
    	notificationDao.insertNotice(notice);
    	
    }
    	
    public void markAsRead(String loginId) {
    	notificationDao.markAsRead(loginId);
	    
	}
}
