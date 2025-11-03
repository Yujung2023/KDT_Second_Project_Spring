
package com.kedu.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kedu.project.dto.NotificationDTO;
import com.kedu.project.service.NotificationService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

	@Autowired
	NotificationService notificationService;

	private final SimpMessagingTemplate messagingTemplate;

	// ✅ STOMP를 통한 직접 메시지 전송 (/app/notice.send)
	@MessageMapping("/notice.send")
	public void sendNotice(NotificationDTO notice) {
		messagingTemplate.convertAndSend("/notice/" + notice.getReceiver_id(), notice);
	}

	// ✅ REST API로도 보낼 수 있음 
	@PostMapping("/send")
	public void sendNoticeRest(@RequestBody NotificationDTO notice, HttpServletRequest request) {
		String loginId = (String) request.getAttribute("loginID");


		notice.setSender_id(loginId);
		System.out.println("알림 테스트: " + notice.getReceiver_id());
		System.out.println("알림 테스트: " + notice.getType());
		System.out.println("알림 테스트: " + notice.getMessage());

		// 📦 JSON으로 직렬화되었는지 확인 로그
		System.out.println("보내는 데이터: " + notice);


		try {
			ObjectMapper mapper = new ObjectMapper();
			System.out.println("📦 JSON 변환 테스트: " + mapper.writeValueAsString(notice));
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("json변환 에러");
		}


		if(!loginId.equals(notice.getReceiver_id()))
		{
			messagingTemplate.convertAndSend("/notice/" + notice.getReceiver_id(), notice);
			notificationService.insertNotice(notice);

			System.out.println("📤 알림 전송 완료 → /notice/" + notice.getReceiver_id());
		}
		else
		{
			System.out.println("같은 아이디입니다.");
		}
	}
	@GetMapping
	public ResponseEntity<List<NotificationDTO>> getNotificationByLoginId(HttpServletRequest request) {
		String loginId = (String) request.getAttribute("loginID");

		List<NotificationDTO> list = notificationService.getNotificationByLoginId(loginId);
		return ResponseEntity.ok(list);
	}

	@PutMapping("/read")
	public ResponseEntity<Void> markAsRead(HttpServletRequest request) {
		String loginId = (String) request.getAttribute("loginID");
	    notificationService.markAsRead(loginId);
	    return ResponseEntity.ok().build();
	}

}