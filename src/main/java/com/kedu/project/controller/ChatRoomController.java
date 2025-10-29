package com.kedu.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.entity.ChatMessage;
import com.kedu.project.entity.ChatRoom;
import com.kedu.project.repository.ChatMessageRepository;
import com.kedu.project.service.ChatRoomService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageRepository chatMessageRepo; // ✅ 메시지 조회를 위해 추가

    /**
     *  방 생성 또는 기존 방 조회
     * key: "user01_user02" 형태 (정렬된 값)
     * Return: ChatRoom 정보 (roomId 포함)
     */
    @PostMapping("/room")
    public ChatRoom createOrGetRoom(@RequestParam String key) {
        return chatRoomService.getOrCreateRoom(key);
    }

    /**
     *  로그인한 사용자의 채팅방 목록 조회
     * JWT Filter에서 request.setAttribute("loginID", id) 로 전달되는 값 사용
     */
    @GetMapping("/rooms")
    public ResponseEntity<?> getMyRooms(HttpServletRequest request) {

        String userId = (String) request.getAttribute("loginID");
        if (userId == null) {
            return ResponseEntity.status(401).body("로그인 필요");
        }

        List<Map<String, Object>> rooms = chatRoomService.getMyChatRooms(userId);

        return ResponseEntity.ok(rooms);
    }

    /**
     * ✅ 특정 채팅방의 기존 채팅 메시지 로드
     * roomId 기준 정렬된 데이터 반환
     * ChatRoom.jsx에서 최초 로딩 시 호출됨
     */
    @GetMapping("/messages/{roomId}")
    public ResponseEntity<?> getMessages(@PathVariable String roomId) {

        List<ChatMessage> messages = chatMessageRepo.findByRoom_RoomIdOrderBySendTimeAsc(roomId);

        List<Map<String, Object>> result = messages.stream().map(m -> {
            Map<String, Object> map = new HashMap<>();
            map.put("sender", m.getSender());
            map.put("content", m.getContent());
            map.put("sendTime", m.getSendTime());
            map.put("type", m.getType());
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
