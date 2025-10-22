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

@Service
@EnableScheduling
public class AttendanceService {

	
	@Autowired
	private AttendanceDAO dao;
	
	//근태 현황
	public List<Map<String, Object>> countSelect(String member_id) {
	    return dao.countSelect(member_id);
	}
	
	
	//지각 처리
	public void CheckIn(String member_id) {
		LocalDateTime now=LocalDateTime.now();
		LocalDateTime baseStart=LocalDate.now().atTime(9,0);//9시
		String status=now.isAfter(baseStart) ? "late" : "normal";
		dao.insertCheckIn(member_id, status);
	}
	
	//퇴근 처리
		public void CheckOut(String member_id) {
			LocalDateTime now=LocalDateTime.now();
			LocalDateTime baseStart=LocalDate.now().atTime(18,0); //18시
			String status=now.isBefore(baseStart) ? "earlyleave" : "done";
			dao.exitCheckIn(member_id, status);
		}
		
		//퇴근 미체크
		@Scheduled(cron="0 15 19 * * *")
		public void autonoCheck() {
			dao.autoNoCheck();
			System.out.println("퇴근 미체크함 수고");
		}
		
			//새로 고침해도 유지
		public Map<String, Object> getToday(String member_id){
			return dao.getToday(member_id);
		}
		
		
		@Scheduled(cron="0 04 16 * * *")
		public void autoAbsence() {
		    dao.autoAbsence();
		    System.out.println("결근 자동 처리 완료");
		}


}
