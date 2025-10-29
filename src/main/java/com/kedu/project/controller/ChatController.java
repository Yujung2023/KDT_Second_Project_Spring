package com.kedu.project.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.kedu.project.dto.ChatMessageDTO;
import com.kedu.project.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그 출력용 어노테이션
@Controller // WebSocket 메시지 처리 컨트롤러
@RequiredArgsConstructor // 생성자 자동 주입
public class ChatController {

    // ChatMessageService 주입 (메시지 저장 담당)
    private final ChatService ChatService;

    /**
     * 클라이언트로부터 메시지 수신
     * WebSocket publish 경로: /app/chat/{roomId}
     * 구독자에게 메시지 전송: /topic/chatroom/{roomId}
     */
    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/chatroom/{roomId}")
    public ChatMessageDTO sendMessage(
            @DestinationVariable String roomId,
            ChatMessageDTO message
    ) {
        log.info("Message Received. roomId={}, sender={}, content={}",
                roomId, message.getSender(), message.getContent());

        // room_id가 없을 경우 URL 경로에서 받은 roomId로 설정
        message.setRoom_id(roomId);

        // DB 저장 및 클라이언트에게 전달할 DTO 반환
        return ChatService.saveMessage(roomId, message);
    }

    /**
     * 사용자 입장 알림 메시지 처리
     */
    @MessageMapping("/chat/enter/{roomId}")
    @SendTo("/topic/chatroom/{roomId}")
    public ChatMessageDTO enterRoom(
            @DestinationVariable String roomId,
            ChatMessageDTO message
    ) {
        log.info("User Enter. roomId={}, sender={}", roomId, message.getSender());

        message.setRoom_id(roomId);
        message.setType("ENTER"); // String 기반 타입
        message.setContent(message.getSender() + "님이 입장했습니다.");

        return ChatService.saveMessage(roomId, message);
    }

    /**
     * 사용자 퇴장 알림 메시지 처리
     */
    @MessageMapping("/chat/leave/{roomId}")
    @SendTo("/topic/chatroom/{roomId}")
    public ChatMessageDTO leaveRoom(
            @DestinationVariable String roomId,
            ChatMessageDTO message
    ) {
        log.info("User Leave. roomId={}, sender={}", roomId, message.getSender());

        message.setRoom_id(roomId);
        message.setType("LEAVE");
        message.setContent(message.getSender() + "님이 퇴장했습니다.");

        return ChatService.saveMessage(roomId, message);
    }
}
