//package com.kedu.project.controller;
//
//import java.util.List;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import com.kedu.project.dto.ChatMessageDTO;
//import com.kedu.project.dto.MemberSimpleDTO;
//import com.kedu.project.service.ChatRoomService;
//import com.kedu.project.service.ChatService;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Controller
//@RequiredArgsConstructor
//public class ChatController {
//
//    private final ChatService chatService;
//    private final ChatRoomService chatRoomService;   // ✅ 추가
//    private final SimpMessagingTemplate template;
//
//    /**  일반 채팅 메시지 송신 */
//    @MessageMapping("/chat/{roomId}")
//    @SendTo("/topic/chatroom/{roomId}")
//    public ChatMessageDTO sendMessage(
//            @DestinationVariable String roomId,
//            ChatMessageDTO message
//    ) {
//        message.setRoomId(roomId);
//        if (message.getType() == null) message.setType("TALK");
//        return chatService.saveMessage(roomId, message);
//    }
//
//    /**  읽음 이벤트 (SendTo 사용 안 함) */
//    @MessageMapping("/chat/read/{roomId}")
//    public void readMessage(
//            @DestinationVariable String roomId,
//            ChatMessageDTO message
//    ) {
//        ChatMessageDTO updated = chatService.markRead(roomId, message.getMessageId(), message.getSender());
//        template.convertAndSend("/topic/chatroom/" + roomId, updated);
//    }
//
//    /**  참여자 목록 조회 API (이름 + 직급만 반환) */
//    @GetMapping("/api/chat/members/{roomId}")
//    public ResponseEntity<List<MemberSimpleDTO>> getRoomMembers(@PathVariable String roomId) {
//        List<MemberSimpleDTO> members = chatRoomService.getRoomMembers(roomId);
//        return ResponseEntity.ok(members);
//    }
//}
