package com.kedu.project.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.kedu.project.dto.ChatMessageDTO;

@Controller
public class ChatController {
    @MessageMapping("/chat") // 클라이언트가 /app/chat 으로 보낸 메시지 처리
    @SendTo("/topic/chat")   // 처리 후 /topic/chat 구독자에게 브로드캐스트
    public ChatMessageDTO send(ChatMessageDTO message) {
        return message;
    }
}