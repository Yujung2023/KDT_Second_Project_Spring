package com.kedu.project.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.NotificationDTO;

@Repository
public class NotificationDAO {
	
	@Autowired
	private SqlSession mybatis;
	
    public List<NotificationDTO> getNotificationByLoginId(String loginId) {
        return mybatis.selectList("Notification.getNotificationByLoginId",loginId);
    }
    

    public void insertNotice(NotificationDTO notice) {
    	mybatis.insert("Notification.insertNotice",notice);
    	
    }
    

    public void markAsRead(String loginId) {
    	mybatis.update("Notification.markAsRead",loginId);
	    
	}
    	
}
