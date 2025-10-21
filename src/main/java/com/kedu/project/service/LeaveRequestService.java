package com.kedu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.LeaveRequestDAO;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestDAO leaveRequestDAO;

    public int getRemainLeave(String memberId) {
        Integer result = leaveRequestDAO.selectRemainLeave(memberId);
        return result != null ? result : 0;
    }
}
