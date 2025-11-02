package com.kedu.project.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedu.project.dto.ChatMessageDTO;
import com.kedu.project.entity.ChatMessage;
import com.kedu.project.entity.ChatMessageRead;
import com.kedu.project.entity.ChatMessageReadId;
import com.kedu.project.entity.ChatRoom;
import com.kedu.project.entity.ChatRoomMember;
import com.kedu.project.entity.ChatRoomMemberId;
import com.kedu.project.entity.Member;
import com.kedu.project.repository.ChatMessageReadRepository;
import com.kedu.project.repository.ChatMessageRepository;
import com.kedu.project.repository.ChatRoomMemberRepository;
import com.kedu.project.repository.ChatRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final ChatRoomRepository roomRepo;
    private final ChatRoomMemberRepository roomMemberRepo;
    private final ChatMessageRepository msgRepo;
    private final ChatMessageReadRepository readRepo;

    /** ✅ 메시지 저장: 보낸 즉시 보낸 사람 읽음 처리, 방 최신정보 갱신, 읽음수 계산 */
    public ChatMessageDTO saveMessage(String roomId, ChatMessageDTO dto) {
        ChatRoom room = roomRepo.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("room not found: " + roomId));

        ChatMessage.MessageType type = parseType(dto.getType());

        ChatMessage msg = ChatMessage.builder()
                .room(room)
                .sender(require(dto.getSender(), "sender"))
                .content(dto.getContent())
                .fileUrl(dto.getFileUrl())
                .type(type == null ? ChatMessage.MessageType.TALK : type)
                .sendTime(LocalDateTime.now())
                .build();

        msg = msgRepo.save(msg);

        // ✅ 보낸 사람은 즉시 읽음 처리
        markReadInternal(msg.getId(), dto.getSender());

        // ✅ 보낸 사람의 lastReadMessageId 갱신
        ChatRoomMemberId id = new ChatRoomMemberId(roomId, dto.getSender());
        ChatRoomMember crm = roomMemberRepo.findById(id).orElse(null);
        if (crm != null) {
            crm.setLastReadMessageId(msg.getId());
            roomMemberRepo.save(crm);
        }

        // ✅ 방 최신 정보 갱신
        room.setLastMessage(preview(type, dto.getContent()));
        room.setLastUpdatedAt(LocalDateTime.now());

        // ✅ 읽음 수 계산
        int memberCount = roomMemberRepo.countMembers(roomId);
        int readers = readRepo.countReaders(msg.getId());
        int unread = Math.max(0, memberCount - readers);

        return toDTO(msg, unread);
    }


    /** ✅ 읽음 이벤트 처리 */
    public ChatMessageDTO markRead(String roomId, Long messageId, String memberId) {
        ChatMessage msg = msgRepo.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("message not found: " + messageId));

        // ✅ 읽음 기록 (중복 방지)
        markReadInternal(messageId, memberId);

        // ✅ 내 마지막 읽은 메시지 ID 갱신
        ChatRoomMemberId id = new ChatRoomMemberId(roomId, memberId);
        ChatRoomMember crm = roomMemberRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("room-member not found: " + id));

        if (crm.getLastReadMessageId() == null || crm.getLastReadMessageId() < messageId) {
            crm.setLastReadMessageId(messageId);
            roomMemberRepo.save(crm); // ⚡ DB 반영
        }

        // ✅ 남은 미읽은 인원 수 계산
        int totalMembers = roomMemberRepo.countMembers(roomId);
        int readers = readRepo.countReaders(messageId);
        int unread = Math.max(0, totalMembers - readers);

        ChatMessageDTO dto = toDTO(msg, unread);
        dto.setType("READ_UPDATE"); // 실시간 읽음수 갱신용 이벤트
        return dto;
    }

    // ===== 내부 유틸 =====

    private void markReadInternal(Long messageId, String memberId) {
        ChatMessageReadId rid = new ChatMessageReadId(messageId, memberId);
        if (!readRepo.existsById(rid)) {
            ChatMessageRead r = ChatMessageRead.builder()
                    .id(rid)
                    .message(ChatMessage.builder().id(messageId).build())
                    .member(Member.builder().id(memberId).build())
                    .readTime(LocalDateTime.now())
                    .build();
            readRepo.save(r);
        }
    }

    private ChatMessageDTO toDTO(ChatMessage m, int unread) {
        return ChatMessageDTO.builder()
                .messageId(m.getId())
                .roomId(m.getRoom().getRoomId())
                .sender(m.getSender())
                .content(m.getContent())
                .fileUrl(m.getFileUrl())
                .type(m.getType().name())
                .sendTime(m.getSendTime())
                .readCount(unread)
                .build();
    }

    private String preview(ChatMessage.MessageType type, String content) {
        if (type == ChatMessage.MessageType.FILE) return "파일을 보냈습니다";
        if (content == null) return null;
        String t = content.strip();
        return t.length() > 40 ? t.substring(0, 40) + "…" : t;
    }

    private ChatMessage.MessageType parseType(String t) {
        if (t == null) return null;
        try {
            return ChatMessage.MessageType.valueOf(t);
        } catch (Exception ignore) {
            return null;
        }
    }

    private String require(String v, String name) {
        if (v == null || v.trim().isEmpty())
            throw new IllegalArgumentException(name + " is required");
        return v;
    }
}
