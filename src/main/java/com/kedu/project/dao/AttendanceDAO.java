package com.kedu.project.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kedu.project.service.AttendanceService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.AttendanceDTO;

@Repository
public class AttendanceDAO {

    @Autowired
    private SqlSession mybatis;

    // 근태 근황
    public List<Map<String, Object>> countSelect(String member_id) {
        return mybatis.selectList("attendance.CountSelect", member_id);
    }

    // 출근 insert
    public int insertCheckIn(String member_id, String status) {
        Map<String,Object> params = new HashMap<>();
        params.put("member_id", member_id);
        params.put("start_status", status);
        return mybatis.insert("attendance.insertCheckIn", params);
    }

    // 퇴근 update
    public int exitCheckIn(String member_id, String status) {
        Map<String,Object> params = new HashMap<>();
        params.put("member_id", member_id);
        params.put("end_status", status);
        return mybatis.update("attendance.exitCheckIn", params);
    }

    // 퇴근 미체크 처리 update
    public int autoNoCheck() {
        return mybatis.update("attendance.autoNoCheck");
    }
    
    
    public int autoAbsence() {
        return mybatis.insert("attendance.autoAbsence");
    }
    
    

    // 2) 결근 → 출근 UPDATE (출근 버튼 눌렀을 때)
    public int updateCheckIn(String member_id, String status) {
        Map<String, Object> params = new HashMap<>();
        params.put("member_id", member_id);
        params.put("status", status);
        return mybatis.update("attendance.updateCheckIn", params);
    }

    // 3) 퇴근 UPDATE (명확하게 분리할 경우)
    public int updateCheckOut(String member_id, String status) {
        Map<String, Object> params = new HashMap<>();
        params.put("member_id", member_id);
        params.put("status", status);
        return mybatis.update("attendance.updateCheckOut", params);
    }

    
    
    
    //실시간 처리 
    public Map<String, Object> getToday(String member_id) {
        return mybatis.selectOne("attendance.getToday", member_id);
    }
    
}

