package com.kedu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedu.project.dao.HomeLayoutDAO;
import com.kedu.project.dto.HomeLayoutDTO;

@Service
public class HomeLayoutService {

    @Autowired
    private HomeLayoutDAO dao;

    // ✅ 저장 (insert or update)
    
    @Transactional
    public void saveLayout(String userId, String layoutJson) {
        HomeLayoutDTO dto = new HomeLayoutDTO();
        dto.setUser_Id(userId);
        dto.setLayout_Json(layoutJson);
        
        System.out.println("여기까지옴2");
        if (dao.existsByUserId(userId)) {
            dao.updateLayout(dto);
        } else {
            dao.insertLayout(dto);
        }
    }

    // ✅ 조회
    public HomeLayoutDTO getLayout(String userId) {
        return dao.getLayoutByUser(userId);
    }
}
