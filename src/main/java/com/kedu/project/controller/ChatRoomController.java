package com.kedu.project.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.ChatMessageDTO;
import com.kedu.project.dto.MemberSimpleDTO;
import com.kedu.project.repository.ChatMessageReadRepository;
import com.kedu.project.repository.ChatMessageRepository;
import com.kedu.project.repository.ChatRoomMemberRepository;
import com.kedu.project.service.ChatRoomService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatRoomController {  //  기존 이름 유지

    private final ChatRoomService chatRoomService;
    private final ChatMessageRepository chatMessageRepo;
    private final ChatRoomMemberRepository roomMemberRepo;
    private final ChatMessageReadRepository readRepo;

    private final SimpMessagingTemplate template; //  추가

    @PostMapping("/room")
    public ResponseEntity<?> createOrGetRoom(@RequestParam("key") String key) {
        String[] arr = Optional.ofNullable(key).orElse("").split("_");
        if (arr.length != 2)
            return ResponseEntity.badRequest().body("key must be 'userA_userB'");

        String userA = arr[0];
        String userB = arr[1];

        var room = chatRoomService.getOrCreateDmRoom(userA, userB);

        //  상대방에게 STOMP로 실시간 새 방 알림 전송
        try {
            template.convertAndSend(
                "/topic/user/" + userB,
                Map.of("type", "NEW_ROOM", "roomId", room.getRoomId(), "from", userA)
            );
            template.convertAndSend(
                "/topic/user/" + userA,
                Map.of("type", "NEW_ROOM", "roomId", room.getRoomId(), "from", userB)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(room);
    }


    /**  내 방 목록 */
    @GetMapping("/rooms")
    public ResponseEntity<?> getMyRooms(HttpServletRequest request) {
        String userId = (String) request.getAttribute("loginID");
        if (userId == null) return ResponseEntity.status(401).body("로그인 필요");
        return ResponseEntity.ok(chatRoomService.getMyChatRooms(userId));
    }

    /**  메시지 로드 */
    @GetMapping("/messages/{roomId}")
    public ResponseEntity<?> getMessages(@PathVariable String roomId) {
        var list = chatMessageRepo.findByRoom_RoomIdOrderBySendTimeAsc(roomId);
        int memberCount = roomMemberRepo.countMembers(roomId);

        var result = list.stream().map(m -> {
            int readers = readRepo.countReaders(m.getId());
            int unread = Math.max(0, memberCount - readers);
            return ChatMessageDTO.builder()
                    .messageId(m.getId())
                    .roomId(roomId)
                    .sender(m.getSender())
                    .content(m.getContent())
                    .fileUrl(m.getFileUrl())
                    .type(m.getType().name())
                    .sendTime(m.getSendTime())
                    .readCount(unread)
                    .build();
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    /**  초대 */
    @PostMapping("/invite")
    public ResponseEntity<?> invite(@RequestParam String roomId,
                                    @RequestParam List<String> memberIds) {
        chatRoomService.inviteMembers(roomId, memberIds);
        return ResponseEntity.ok("OK");
    }
    /**  방 나가기 */
    @PostMapping("/leave")
    public ResponseEntity<?> leaveRoom(
            @RequestParam String roomId,
            HttpServletRequest request) {

        String memberId = (String) request.getAttribute("loginID");
        if (memberId == null) return ResponseEntity.status(401).body("로그인 필요");

        try {
            boolean deleted = chatRoomService.leaveRoom(roomId, memberId);
            if (deleted) {
                return ResponseEntity.ok("방을 나갔습니다. (마지막 참여자였으므로 방 삭제됨)");
            } else {
                return ResponseEntity.ok("방을 나갔습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("방 나가기 실패");
        }
    }

    /** ✅ 시스템 메시지 저장 (초대, 퇴장 등) */
    @PostMapping("/systemMessage")
    public ResponseEntity<?> postSystemMessage(@RequestBody ChatMessageDTO dto) {
        try {
            chatRoomService.saveSystemMessage(dto);
            return ResponseEntity.ok("SYSTEM message saved");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("SYSTEM 메시지 저장 실패");
        }
    }
    
    /**  참여자 목록 조회 API (이름 + 직급만 반환) */
    @GetMapping("/members/{roomId}")
    public ResponseEntity<List<MemberSimpleDTO>> getRoomMembers(@PathVariable String roomId) {
        List<MemberSimpleDTO> members = chatRoomService.getRoomMembers(roomId);
        return ResponseEntity.ok(members);
    }
    
}
