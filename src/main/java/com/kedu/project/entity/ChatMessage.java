package com.kedu.project.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // 메시지 PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom room;   // 채팅방 (FK)

    private String sender;   // 보낸 사람
    private String content;  // 메시지 내용
    private String fileUrl;  // 파일 경로

    @Enumerated(EnumType.STRING)
    private MessageType type; // 메시지 타입

    private LocalDateTime sendTime;

    // 읽음 카운트 (그룹 채팅에서 몇 명이 읽었는지)
    private int readCount;

    public enum MessageType {
        ENTER, TALK, LEAVE, SYSTEM, FILE
    }
}