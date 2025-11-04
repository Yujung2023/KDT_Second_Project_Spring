package com.kedu.project.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedu.project.dto.ChatMessageDTO;
import com.kedu.project.dto.MemberSimpleDTO;
import com.kedu.project.entity.ChatMessage;
import com.kedu.project.entity.ChatRoom;
import com.kedu.project.entity.ChatRoomMember;
import com.kedu.project.entity.ChatRoomMemberId;
import com.kedu.project.entity.Member;
import com.kedu.project.repository.ChatMessageReadRepository;
import com.kedu.project.repository.ChatMessageRepository;
import com.kedu.project.repository.ChatRoomMemberRepository;
import com.kedu.project.repository.ChatRoomRepository;
import com.kedu.project.repository.MemberRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {

    private final ChatRoomRepository roomRepo;
    private final ChatRoomMemberRepository roomMemberRepo;
    private final ChatMessageRepository msgRepo;
    private final ChatMessageReadRepository readRepo;
    private final MemberRepository memberRepo;
    private final SimpMessagingTemplate template; // ✅ STOMP 메시지 전송용
    /* 멤버 추가 (중복 방지) */
    @PersistenceContext
    private EntityManager em;
    
    
    /**  1:1 방 있으면 재사용, 없으면 새로 생성 */
    public ChatRoom getOrCreateDmRoom(String userA, String userB) {
        String u1 = Objects.requireNonNull(userA).trim();
        String u2 = Objects.requireNonNull(userB).trim();
        if (u1.equals(u2)) throw new IllegalArgumentException("same user");

        List<ChatRoom> cand = roomRepo.findRoomsOf(u1);
        for (ChatRoom r : cand) {
            Set<String> mids = r.getMembers().stream()
                    .map(m -> m.getMember().getId())
                    .collect(Collectors.toSet());

            if (mids.size() == 2 && mids.contains(u1) && mids.contains(u2)) {
                return r; // 재사용
            }
        }

        ChatRoom room = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(null)
                .lastUpdatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        room = roomRepo.save(room);

        addMember(room, u1);
        addMember(room, u2);

        //  상대방에게 새 방 생성 알림 전송
        Map<String, Object> payload = new HashMap<>();
        payload.put("type", "NEW_ROOM");
        payload.put("roomId", room.getRoomId());
        payload.put("from", u1);

     
        return room;
    }

    /** ✅ 그룹 채팅방 생성 */
    public ChatRoom createGroupRoom(String roomName, List<String> memberIds) {
        if (memberIds == null || memberIds.size() < 2)
            throw new IllegalArgumentException("at least 2 members");

        ChatRoom room = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(roomName)
                .lastUpdatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        room = roomRepo.save(room);

        for (String mid : new LinkedHashSet<>(memberIds)) {
            addMember(room, mid);
        }
        return room;
    }

    /** ✅ 공통 시스템 메시지 생성 및 STOMP 브로드캐스트 */
    public void sendSystemMessage(String roomId, String content) {
        ChatRoom room = roomRepo.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("room not found: " + roomId));

        ChatMessage msg = ChatMessage.builder()
                .room(room)
                .sender("system")
                .content(content)
                .type(ChatMessage.MessageType.SYSTEM)
                .sendTime(LocalDateTime.now())
                .build();

        msgRepo.save(msg);
        template.convertAndSend("/topic/chatroom/" + roomId, msg); // ✅ 실시간 전송
    }

    /** ✅ 초대 시 시스템 메시지 추가 */
    public void inviteMembers(String roomId, List<String> memberIds) {
        ChatRoom room = roomRepo.findById(roomId).orElseThrow();
        boolean added = false;

        for (String mid : memberIds) {
            ChatRoomMemberId id = new ChatRoomMemberId(roomId, mid);
            if (roomMemberRepo.existsById(id)) continue;
            addMember(room, mid);
            added = true;
        }

        if (added) {
            List<Member> addedMembers = memberRepo.findAllById(memberIds);
            String joinedNames = addedMembers.stream()
                    .map(m -> m.getName() + " " + convertRankCode(m.getRank_code()))
                    .collect(Collectors.joining(", "));

            sendSystemMessage(roomId, joinedNames + " 님을 초대하였습니다."); // ✅ 시스템메시지
        }
    }

    
    /** ✅ 방 나가기 (SYSTEM 메시지 자동 추가 및 방송) */
    @Transactional
    public boolean leaveRoom(String roomId, String memberId) {

        // 1️⃣ 내가 속한 멤버 관계 삭제
        roomMemberRepo.deleteByRoomIdAndMemberId(roomId, memberId);

        // 2️⃣ 남은 인원 수 확인
        int remaining = roomMemberRepo.countMembers(roomId);

        // ✅ 퇴장자 정보 조회
        Member leaver = memberRepo.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("member not found: " + memberId));
        String leaverName = leaver.getName() + " " + convertRankCode(leaver.getRank_code());

        // ✅ 방 정보 조회
        ChatRoom room = roomRepo.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("room not found: " + roomId));

        // ✅ 시스템 메시지 생성
        ChatMessage leaveMsg = ChatMessage.builder()
                .room(room)
                .sender("system")
                .content(leaverName + " 님이 나가셨습니다.")
                .type(ChatMessage.MessageType.SYSTEM)
                .sendTime(LocalDateTime.now())
                .build();

        msgRepo.save(leaveMsg);

        // ✅ STOMP 브로드캐스트 (남은 사람에게 표시)
        template.convertAndSend("/topic/chatroom/" + roomId, leaveMsg);

        // 3️⃣ 남은 인원 없으면 → 방 관련 전부 삭제
        if (remaining == 0) {
            try {
                readRepo.deleteByMessage_Room_RoomId(roomId);
                msgRepo.deleteByRoom_RoomId(roomId);
                roomRepo.deleteById(roomId);
            } catch (Exception e) {
                throw new RuntimeException("채팅방 삭제 중 오류 발생: " + e.getMessage(), e);
            }
            return true; // 마지막 참여자 → 방 완전 삭제됨
        }

        return false; // 나만 나가고 방은 유지
    }



    /** ✅ 내 방 목록 조회 */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getMyChatRooms(String userId) {
        List<ChatRoom> rooms = roomRepo.findRoomsOf(userId);

        return rooms.stream().map(r -> {
            Map<String, Object> m = new HashMap<>();
            m.put("roomId", r.getRoomId());
            m.put("roomName", r.getRoomName());
            m.put("lastMessage", r.getLastMessage());
            m.put("lastUpdatedAt", r.getLastUpdatedAt());

            ChatRoomMemberId id = new ChatRoomMemberId(r.getRoomId(), userId);
            Long lastReadId = roomMemberRepo.findById(id)
                    .map(ChatRoomMember::getLastReadMessageId)
                    .orElse(0L);

            int unreadRoomCount = countUnreadRoomMessages(r.getRoomId(), lastReadId, userId);
            m.put("unread", unreadRoomCount);

            List<String> mids = r.getMembers().stream()
                    .map(crm -> crm.getMember().getId())
                    .filter(mid -> !mid.equals(userId))
                    .toList();

            if (mids.size() == 1) {
                memberRepo.findById(mids.get(0)).ifPresent(target -> {
                    m.put("targetId", target.getId());
                    m.put("targetName", target.getName());
                    m.put("targetRank", target.getRank_code());
                    m.put("avatar", target.getProfileImage_servName());
                });
            }
            return m;
        }).toList();
    }

    /** ✅ 특정 방의 모든 참여자 이름/직급 조회 */
    @Transactional(readOnly = true)
    public List<MemberSimpleDTO> getRoomMembers(String roomId) {
        ChatRoom room = roomRepo.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("room not found: " + roomId));

        return room.getMembers().stream()
                .map(crm -> {
                    Member member = crm.getMember();
                    return new MemberSimpleDTO(
                            member.getId(),
                            member.getName(),
                            convertRankCode(member.getRank_code())
                    );
                })
                .toList();
    }

    /** ✅ 직급 코드 → 직급명 */
    private String convertRankCode(String code) {
        return switch (code) {
            case "J001" -> "사원";
            case "J002" -> "주임";
            case "J003" -> "대리";
            case "J004" -> "과장";
            case "J005" -> "차장";
            case "J006" -> "부장";
            case "J007" -> "이사";
            case "J008" -> "부사장";
            case "J009" -> "사장";
            default -> "";
        };
    }

  

    @Transactional
    private void addMember(ChatRoom room, String memberId) {
        if (room.getMembers().stream()
                .anyMatch(m -> m.getMember().getId().equals(memberId)))
            return;

        ChatRoomMemberId id = new ChatRoomMemberId(room.getRoomId(), memberId);
        if (roomMemberRepo.existsById(id)) return;

        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("member not found: " + memberId));

        ChatRoomMember crm = ChatRoomMember.builder()
                .id(id)
                .room(room)
                .member(member)
                .joinedAt(LocalDateTime.now())
                .build();

        room.getMembers().add(crm); // ✅ CascadeType.ALL 이면 persist 자동됨
    }



    /**  안 읽은 메시지 수 계산 */
    private int countUnreadRoomMessages(String roomId, Long lastReadId, String me) {
        try {
            return msgRepo.countByRoom_RoomIdAndIdGreaterThanAndSenderNot(
                roomId,
                lastReadId == null ? 0L : lastReadId,
                me
            );
        } catch (Throwable t) {
            return (int) msgRepo.findByRoom_RoomIdOrderBySendTimeAsc(roomId).stream()
                .filter(m -> m.getId() != null &&
                             m.getId() > (lastReadId == null ? 0L : lastReadId) &&
                             !m.getSender().equals(me))
                .count();
        }
    }

    /** ✅ 직접 호출용 시스템 메시지 저장 (Controller에서 API 호출용) */
    public ChatMessage saveSystemMessage(ChatMessageDTO dto) {
        ChatRoom room = roomRepo.findById(dto.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("room not found: " + dto.getRoomId()));

        ChatMessage msg = ChatMessage.builder()
                .room(room)
                .sender("system")
                .content(dto.getContent())
                .type(ChatMessage.MessageType.SYSTEM)
                .sendTime(LocalDateTime.now())
                .build();

        return msgRepo.save(msg);
    }
}
