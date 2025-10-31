package com.kedu.project.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.ChatMessageDTO;
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

    /**  DM 생성/조회 */
    @PostMapping("/room")
    public ResponseEntity<?> createOrGetRoom(@RequestParam("key") String key) {
        String[] arr = Optional.ofNullable(key).orElse("").split("_");
        if (arr.length != 2) return ResponseEntity.badRequest().body("key must be 'userA_userB'");
        var room = chatRoomService.getOrCreateDmRoom(arr[0], arr[1]);
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

}
