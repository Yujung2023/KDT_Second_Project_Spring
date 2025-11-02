//package com.kedu.project.service;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Set;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.kedu.project.dto.MemberSimpleDTO;
//import com.kedu.project.entity.ChatRoom;
//import com.kedu.project.entity.ChatRoomMember;
//import com.kedu.project.entity.ChatRoomMemberId;
//import com.kedu.project.entity.Member;
//import com.kedu.project.repository.ChatMessageReadRepository;
//import com.kedu.project.repository.ChatMessageRepository;
//import com.kedu.project.repository.ChatRoomMemberRepository;
//import com.kedu.project.repository.ChatRoomRepository;
//import com.kedu.project.repository.MemberRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class ChatRoomService {
//
//    private final ChatRoomRepository roomRepo;
//    private final ChatRoomMemberRepository roomMemberRepo;
//    private final ChatMessageRepository msgRepo;
//    private final ChatMessageReadRepository readRepo;
//    private final MemberRepository memberRepo;
//
//    /** A 선택: 1:1 방은 있으면 재사용, 없으면 생성 */
//    public ChatRoom getOrCreateDmRoom(String userA, String userB) {
//        String u1 = Objects.requireNonNull(userA).trim();
//        String u2 = Objects.requireNonNull(userB).trim();
//        if (u1.equals(u2)) throw new IllegalArgumentException("same user");
//
//        List<ChatRoom> cand = roomRepo.findRoomsOf(u1);
//        for (ChatRoom r : cand) {
//            Set<String> mids = r.getMembers().stream()
//                    .map(m -> m.getMember().getId())
//                    .collect(Collectors.toSet());
//
//            if (mids.size() == 2 && mids.contains(u1) && mids.contains(u2)) {
//                return r; // 재사용
//            }
//        }
//
//        ChatRoom room = ChatRoom.builder()
//                .roomId(UUID.randomUUID().toString())
//                .roomName(null)
//                .lastUpdatedAt(LocalDateTime.now())
//                .createdAt(LocalDateTime.now())
//                .build();
//        room = roomRepo.save(room);
//
//        addMember(room, u1);
//        addMember(room, u2);
//
//        return room;
//    }
//
//    /** 그룹 채팅방 생성 */
//    public ChatRoom createGroupRoom(String roomName, List<String> memberIds) {
//        if (memberIds == null || memberIds.size() < 2)
//            throw new IllegalArgumentException("at least 2 members");
//
//        ChatRoom room = ChatRoom.builder()
//                .roomId(UUID.randomUUID().toString())
//                .roomName(roomName)
//                .lastUpdatedAt(LocalDateTime.now())
//                .createdAt(LocalDateTime.now())
//                .build();
//        room = roomRepo.save(room);
//
//        for (String mid : new LinkedHashSet<>(memberIds)) {
//            addMember(room, mid);
//        }
//        return room;
//    }
//
//    /** 멤버 초대 (이미 있으면 무시) */
//    public void inviteMembers(String roomId, List<String> memberIds) {
//        ChatRoom room = roomRepo.findById(roomId).orElseThrow();
//        boolean added = false;
//
//        for (String mid : memberIds) {
//            ChatRoomMemberId id = new ChatRoomMemberId(roomId, mid);
//            if (roomMemberRepo.existsById(id)) continue;
//            addMember(room, mid);
//            added = true;
//        }
//
//        //  방 이름 업데이트 (단체방이 된 경우)
//        if (added) {
//            List<String> names = room.getMembers().stream()
//                    .map(m -> m.getMember().getName())
//                    .toList();
//
//            if (names.size() > 2) {
//                room.setRoomName(String.join(", ", names));
//            }
//            room.setLastUpdatedAt(LocalDateTime.now());
//            roomRepo.save(room);
//        }
//    }
//
//    /** 내 방 목록 조회 */
//    @Transactional(readOnly = true)
//    public List<Map<String, Object>> getMyChatRooms(String userId) {
//        List<ChatRoom> rooms = roomRepo.findRoomsOf(userId);
//
//        return rooms.stream().map(r -> {
//            Map<String, Object> m = new HashMap<>();
//            m.put("roomId", r.getRoomId());
//            m.put("roomName", r.getRoomName());
//            m.put("lastMessage", r.getLastMessage());
//            m.put("lastUpdatedAt", r.getLastUpdatedAt());
//
//            ChatRoomMemberId id = new ChatRoomMemberId(r.getRoomId(), userId);
//            Long lastReadId = roomMemberRepo.findById(id)
//                    .map(ChatRoomMember::getLastReadMessageId)
//                    .orElse(0L);
//
//            int unreadRoomCount = countUnreadRoomMessages(r.getRoomId(), lastReadId, userId);
//            m.put("unread", unreadRoomCount);
//
//            List<String> mids = r.getMembers().stream()
//                    .map(crm -> crm.getMember().getId())
//                    .filter(mid -> !mid.equals(userId))
//                    .toList();
//
//            if (mids.size() == 1) {
//                memberRepo.findById(mids.get(0)).ifPresent(target -> {
//                    m.put("targetId", target.getId());
//                    m.put("targetName", target.getName());
//                    m.put("targetRank", target.getRank_code());
//                    m.put("avatar", target.getProfileImage_servName());
//                });
//            }
//            return m;
//        }).toList();
//    }
//
//    // ==================================================
//    /**  특정 방의 모든 참여자 이름/직급 조회 */
//    @Transactional(readOnly = true)
//    public List<MemberSimpleDTO> getRoomMembers(String roomId) {
//        ChatRoom room = roomRepo.findById(roomId)
//                .orElseThrow(() -> new IllegalArgumentException("room not found: " + roomId));
//
//        return room.getMembers().stream()
//                .map(crm -> {
//                    Member member = crm.getMember();
//                    return new MemberSimpleDTO(
//                            member.getId(),
//                            member.getName(),
//                            convertRankCode(member.getRank_code())
//                    );
//                })
//                .toList();
//    }
//
//    /** 직급 코드 → 직급명 변환 */
//    private String convertRankCode(String code) {
//        return switch (code) {
//            case "J001" -> "사원";
//            case "J002" -> "주임";
//            case "J003" -> "대리";
//            case "J004" -> "과장";
//            case "J005" -> "차장";
//            case "J006" -> "부장";
//            case "J007" -> "이사";
//            case "J008" -> "부사장";
//            default -> "";
//        };
//    }
//
//    // ==================================================
//
//    private void addMember(ChatRoom room, String memberId) {
//        ChatRoomMemberId id = new ChatRoomMemberId(room.getRoomId(), memberId);
//        ChatRoomMember existing = roomMemberRepo.findById(id).orElse(null);
//        if (existing != null) {
//            if (!room.getMembers().contains(existing)) {
//                room.getMembers().add(existing);
//            }
//            return;
//        }
//
//        Member member = memberRepo.findById(memberId)
//                .orElseThrow(() -> new IllegalArgumentException("member not found: " + memberId));
//
//        ChatRoomMember crm = ChatRoomMember.builder()
//                .id(id)
//                .room(room)
//                .member(member)
//                .joinedAt(LocalDateTime.now())
//                .lastReadMessageId(null)
//                .build();
//
//        crm = roomMemberRepo.save(crm);
//
//        if (!room.getMembers().contains(crm)) {
//            room.getMembers().add(crm);
//        }
//    }
//
//    /** 안 읽은 메시지 수 계산 */
//    private int countUnreadRoomMessages(String roomId, Long lastReadId, String me) {
//        try {
//            // ✅ 내가 마지막으로 읽은 메시지 이후, 내가 보낸 게 아닌 메시지만 카운트
//            return msgRepo.countByRoom_RoomIdAndIdGreaterThanAndSenderNot(
//                roomId,
//                lastReadId == null ? 0L : lastReadId,
//                me
//            );
//        } catch (Throwable t) {
//            // ✅ 예외 fallback 시에도 내가 보낸 건 제외
//            return (int) msgRepo.findByRoom_RoomIdOrderBySendTimeAsc(roomId).stream()
//                .filter(m ->
//                    m.getId() != null &&
//                    m.getId() > (lastReadId == null ? 0L : lastReadId) &&
//                    !m.getSender().equals(me)
//                )
//                .count();
//        }
//        
//    }
//    /** 방 나가기 (내 멤버 삭제 후, 인원 없으면 방 삭제) */
//    public boolean leaveRoom(String roomId, String memberId) {
//        // 1️⃣ 내 멤버십 삭제
//        roomMemberRepo.deleteByRoomIdAndMemberId(roomId, memberId);
//
//        // 2️⃣ 남은 인원 수 확인
//        int remaining = roomMemberRepo.countMembers(roomId);
//
//        // 3️⃣ 남은 인원 없으면 메시지/읽음기록/방 삭제
//        if (remaining == 0) {
//            msgRepo.deleteByRoom_RoomId(roomId);
//            readRepo.deleteByMessage_Room_RoomId(roomId);
//            roomRepo.deleteById(roomId);
//            return true; // 마지막 참여자 → 방 삭제됨
//        }
//
//        return false; // 단순 나가기만 수행
//    }
//    
//}
