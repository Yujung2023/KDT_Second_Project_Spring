package com.kedu.project.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedu.project.entity.ChatRoom;
import com.kedu.project.entity.Member;
import com.kedu.project.repository.ChatRoomRepository;
import com.kedu.project.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepo;
    private final MemberRepository memberRepo;

    @Transactional
    public ChatRoom getOrCreateRoom(String membersKey) {
        return chatRoomRepo.findByRoomMembers(membersKey)
                .orElseGet(() -> chatRoomRepo.save(
                        ChatRoom.builder()
                                .roomMembers(membersKey)
                                .lastUpdatedAt(LocalDateTime.now())
                                .build()
                ));
    }

    public ChatRoom getRoomById(String roomId) {
        return chatRoomRepo.findById(roomId)
                .orElseThrow(() -> new RuntimeException("채팅방이 존재하지 않습니다: " + roomId));
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getMyChatRooms(String userId) {

        List<ChatRoom> rooms = chatRoomRepo
                .findByRoomMembersContainingOrderByLastUpdatedAtDesc(userId);

        return rooms.stream().map(room -> {

            String[] members = room.getRoomMembers().split("_");
            String targetId = Arrays.stream(members)
                    .filter(id -> !id.equals(userId))
                    .findFirst()
                    .orElse("unknown");

            Optional<Member> target = memberRepo.findById(targetId);

            Map<String, Object> map = new HashMap<>();
            map.put("roomId", room.getRoomId());
            map.put("roomMembers", room.getRoomMembers());
            map.put("lastMessage", room.getLastMessage());
            map.put("lastUpdatedAt", room.getLastUpdatedAt());

            if (target.isPresent()) {
                Member m = target.get();
                map.put("targetId", m.getId());
                map.put("targetName", m.getName());
                map.put("targetRank", m.getRank_code()); // ✅ 수정
                map.put("avatar", m.getProfileImage_servName()); // ✅ 수정
            } else {
                map.put("targetName", "알 수 없음");
                map.put("targetRank", "");
            }

            return map;
        }).collect(Collectors.toList());
    }
}
