package com.kedu.project.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.AttendanceDAO;
import com.kedu.project.dao.MemberDAO;

@Service
@EnableScheduling
public class AttendanceService {

    @Autowired
    private AttendanceDAO dao;

    @Autowired
    private MemberDAO memberDAO; 

    //근태 현황
    public List<Map<String, Object>> countSelect(String member_id) {
        return dao.countSelect(member_id);
    }

    // 출근 처리
    public void CheckIn(String member_id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime baseStart = LocalDate.now().atTime(9, 0); // 9시
        String status = now.isAfter(baseStart) ? "late" : "normal";

        int updated = dao.updateLateFormAbsence(member_id);

        if (updated == 0) {
            dao.insertCheckIn(member_id, status);
        }

        // ✅ 출근 → 근무중
        memberDAO.updateWorkStatus(member_id, "working");
    }

    // 퇴근 처리
    public void CheckOut(String member_id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime baseEnd = LocalDate.now().atTime(18, 0); // 18시
        String status = now.isBefore(baseEnd) ? "earlyleave" : "done";

        dao.exitCheckIn(member_id, status);

        // ✅ 퇴근 → 근무 종료
        memberDAO.updateWorkStatus(member_id, "offline");
    }

    //퇴근 미체크
    @Scheduled(cron="0 00 18 * * *")
    public void autonoCheck() {
        dao.autoNoCheck();
        System.out.println("퇴근 미체크 자동 처리 완료");
    }

    //새로고침 유지
    public Map<String, Object> getToday(String member_id){
        return dao.getToday(member_id);
    }

    // 결근 자동 처리
    @Scheduled(cron="0 00 09 * * *")
    public void autoAbsence() {
        dao.autoAbsence();
        System.out.println("결근 자동 처리 완료");
    }

    public int getMonthlyWorkDays(String memberId) {
        return dao.selectMonthWorkDays(memberId);
    }
}

