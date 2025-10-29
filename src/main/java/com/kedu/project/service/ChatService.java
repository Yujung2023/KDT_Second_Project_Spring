package com.kedu.project.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedu.project.dto.ChatMessageDTO;
import com.kedu.project.entity.ChatMessage;
import com.kedu.project.entity.ChatRoom;
import com.kedu.project.entity.Member;
import com.kedu.project.repository.ChatMessageRepository;
import com.kedu.project.repository.ChatRoomRepository;
import com.kedu.project.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepo;
    private final ChatMessageRepository chatMessageRepo;
    private final MemberRepository memberRepo;

    @Transactional
    public ChatMessageDTO saveMessage(String roomId, ChatMessageDTO dto) {

        ChatRoom room = chatRoomRepo.findById(roomId)
                .orElseThrow(() -> new RuntimeException("채팅방이 존재하지 않습니다. roomId = " + roomId));

        if (dto.getSender() == null || dto.getSender().trim().isEmpty()) {
            throw new IllegalArgumentException("Sender cannot be null or empty");
        }

        ChatMessage.MessageType messageType;
        try {
            messageType = dto.getType() != null
                    ? ChatMessage.MessageType.valueOf(dto.getType())
                    : ChatMessage.MessageType.TALK;
        } catch (Exception e) {
            messageType = ChatMessage.MessageType.TALK;
        }

        ChatMessage entity = ChatMessage.builder()
                .room(room)
                .sender(dto.getSender())
                .content(dto.getContent())
                .fileUrl(dto.getFileUrl())
                .type(messageType)
                .sendTime(LocalDateTime.now())
                .readCount(0)
                .build();

        chatMessageRepo.save(entity);

        room.setLastMessage(entity.getContent());
        room.setLastUpdatedAt(LocalDateTime.now());
        chatRoomRepo.save(room);

        dto.setRoom_id(roomId);
        dto.setSendtime(Timestamp.valueOf(entity.getSendTime()));

        return dto;
    }

    public List<Map<String, Object>> getRoomsByUser(String userId) {
        List<ChatRoom> rooms = chatRoomRepo.findByRoomMembersContaining(userId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (ChatRoom room : rooms) {
            String[] members = room.getRoomMembers().split("_");
            String targetId = Arrays.stream(members)
                    .filter(id -> !id.equals(userId))
                    .findFirst()
                    .orElse("unknown");

            Member target = memberRepo.findById(userId).orElse(null);
            Map<String, Object> dto = new HashMap<>();
            dto.put("roomId", room.getRoomId());
            dto.put("lastMessage", room.getLastMessage());
            dto.put("lastUpdatedAt", room.getLastUpdatedAt());

            if (target != null) {
                dto.put("targetId", target.getId());
                dto.put("targetName", target.getName());
                dto.put("targetRank", target.getRank_code());
                dto.put("avatar", target.getProfileImage_servName());
            }

            dto.put("unread", 0);

            result.add(dto);
        }

        return result;
    }
}
