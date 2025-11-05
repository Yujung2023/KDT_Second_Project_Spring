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
public class ChatRoomController {  

    private final ChatRoomService chatRoomService;
    private final ChatMessageRepository chatMessageRepo;
    private final ChatRoomMemberRepository roomMemberRepo;
    private final ChatMessageReadRepository readRepo;

    private final SimpMessagingTemplate template; //  추가

 
    @PostMapping("/room")
    public ResponseEntity<?> createOrGetRoom(@RequestParam("key") String key) {
        
        // key 파라미터를 받아 "_" 기준으로 분리 (예: "user01_user02" → ["user01", "user02"])
        // Optional.ofNullable(key).orElse("") : key가 null일 경우 빈 문자열로 대체하여 NPE 방지
        String[] arr = Optional.ofNullable(key).orElse("").split("_");

        // 두 명의 ID가 모두 포함되어 있지 않으면 잘못된 요청으로 간주 (예외 처리)
        if (arr.length != 2)
            return ResponseEntity.badRequest().body("key must be 'userA_userB'");

        // 첫 번째 사용자 ID (보낸 사람)
        String userA = arr[0];
        // 두 번째 사용자 ID (받는 사람)
        String userB = arr[1];

        // chatRoomService를 통해 기존 DM 채팅방을 찾거나 없으면 새로 생성
        // getOrCreateDmRoom(userA, userB) 내부에서는 DB에서 userA, userB 조합으로 방을 찾고 없으면 생성
        var room = chatRoomService.getOrCreateDmRoom(userA, userB);

        // 채팅방 생성 후, 상대방들에게 실시간 알림(STOMP) 전송
        try {
            // 상대방 userB에게 새 방 생성 알림 전송
            // 목적지: /topic/user/{userB}
            // payload: JSON 형태 {type:"NEW_ROOM", roomId:방ID, from:userA}
            template.convertAndSend(
                "/topic/user/" + userB,
                Map.of("type", "NEW_ROOM", "roomId", room.getRoomId(), "from", userA)
            );

            // userA 자신에게도 동일한 NEW_ROOM 알림 전송
            // 이렇게 해야 양쪽 UI 모두 실시간으로 방 목록 갱신 가능
            template.convertAndSend(
                "/topic/user/" + userA,
                Map.of("type", "NEW_ROOM", "roomId", room.getRoomId(), "from", userB)
            );

        } catch (Exception e) {
            // STOMP 메시지 전송 중 예외가 발생할 경우 콘솔에 스택 트레이스 출력
            e.printStackTrace();
        }

        // 방 생성 또는 조회 성공 시, room 정보를 HTTP 응답 본문으로 반환 (상태 코드 200)
        return ResponseEntity.ok(room);
    }


    /*  내 방 목록 */
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

    /**  시스템 메시지 저장 (초대, 퇴장 등) */
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
