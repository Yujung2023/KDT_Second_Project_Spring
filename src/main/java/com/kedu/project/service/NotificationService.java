package com.kedu.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.MemberDAO;
import com.kedu.project.dao.NotificationDAO;
import com.kedu.project.dto.MemberDTO;
import com.kedu.project.dto.NotificationDTO;

@Service
public class NotificationService {
	@Autowired
	private NotificationDAO notificationDao;
	@Autowired
	private MemberDAO memberDao;
	
    public List<NotificationDTO> getNotificationByLoginId(String loginId) {
        List<NotificationDTO> list = notificationDao.getNotificationByLoginId(loginId);
        return list;
    }
    
    
    public void insertNotice(NotificationDTO notice) {
    	System.out.println("리시버아디:"+notice.getReceiver_id());
    	notificationDao.insertNotice(notice);
    	
    }
    
    public void insertNoticeAll(NotificationDTO notice) {
    	System.out.println("리시버아디:"+notice.getReceiver_id());

    	List<MemberDTO> memberLists = memberDao.getListMember();
    	for(MemberDTO memberDTO : memberLists) {
    		//receiver_id에 아이디 담아서 보내기.
    		notice.setReceiver_id(memberDTO.getId());
    		notificationDao.insertNotice(notice);
    	}
    }
    
    public void markAsRead(String loginId) {
    	notificationDao.markAsRead(loginId);
	    
	}
}
