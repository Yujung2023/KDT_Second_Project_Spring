package com.kedu.project.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.kedu.project.dto.ChatMessageDTO;
import com.kedu.project.dto.MemberSimpleDTO;
import com.kedu.project.service.ChatRoomService;
import com.kedu.project.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate template;

    /**
     * ✅ 일반 채팅 메시지 송신
     * - /app/chat/{roomId} 로 들어온 메시지를 저장 후
     *   /topic/chatroom/{roomId} 로 브로드캐스트
     * - 동시에 상대방에게 개인 알림(/topic/user/{id})을 전송
     */
    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/chatroom/{roomId}")
    public ChatMessageDTO sendMessage(
            @DestinationVariable String roomId,
            ChatMessageDTO message
    ) {
        // 1️⃣ 메시지 기본 세팅 및 저장
        message.setRoomId(roomId);
        if (message.getType() == null) message.setType("TALK");
        ChatMessageDTO saved = chatService.saveMessage(roomId, message);

        // 2️⃣ 방 참여자 조회 (보낸 사람 제외)
        try {
            List<MemberSimpleDTO> members = chatRoomService.getRoomMembers(roomId);
            List<String> targetIds = members.stream()
                    .map(MemberSimpleDTO::getMemberId)  // ✅ 필드명 수정됨
                    .filter(id -> !id.equals(message.getSender())) // 본인 제외
                    .collect(Collectors.toList());

            // 3️⃣ 상대방들에게 개인 알림 발송
            for (String targetId : targetIds) {
                log.info(" 개인 알림 전송 대상: {}", targetId);
                template.convertAndSend(
                    "/topic/user/" + targetId,
                    Map.of(
                        "type", "NEW_MESSAGE",
                        "roomId", roomId,
                        "sender", message.getSender(),
                        "content", message.getContent()
                    )
                );
            }
        } catch (Exception e) {
            log.error(" 개인 알림 전송 실패:", e);
        }

        // 4️⃣ 원래대로 방 전체에 메시지 브로드캐스트
        return saved;
    }

    /**
     * ✅ 읽음 이벤트
     * - /app/chat/read/{roomId} 로 들어온 읽음 상태를 업데이트 후
     *   /topic/chatroom/{roomId} 로 반영
     */
    @MessageMapping("/chat/read/{roomId}")
    public void readMessage(
            @DestinationVariable String roomId,
            ChatMessageDTO message
    ) {
        ChatMessageDTO updated = chatService.markRead(roomId, message.getMessageId(), message.getSender());
        template.convertAndSend("/topic/chatroom/" + roomId, updated);
    }
}
