package com.kedu.project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.LoginLogDAO;
import com.kedu.project.dto.LoginLogDTO;

@Service
public class LoginLogService {

    @Autowired
    private LoginLogDAO loginLogDAO;

    public List<LoginLogDTO> searchLogs(String startDate, String endDate, String userId, String ip) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("userId", userId);
        params.put("ip", ip);

        return loginLogDAO.searchLogs(params);
    }
    
    public void saveLoginLog(LoginLogDTO loginLogDTO) {
    	loginLogDAO.saveLoginLog(loginLogDTO);
    }
}