package com.kedu.project.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Oracle Trigger에서 자동생성
    @Column(name = "id", nullable = false)
    private Long id;

    // FK : room_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom room;

    @Column(length = 100, nullable = false)
    private String sender;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String content;

    private String fileUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType type = MessageType.TALK; // 기본 TALK

    @Column(columnDefinition = "TIMESTAMP DEFAULT SYSTIMESTAMP")
    private LocalDateTime sendTime;

    @Column(columnDefinition = "NUMBER DEFAULT 0")
    private int readCount;

    @PrePersist
    protected void onCreate() {
        if (sendTime == null) sendTime = LocalDateTime.now();
        if (readCount == 0) readCount = 0;
    }

    public enum MessageType {
        ENTER, TALK, LEAVE, SYSTEM, FILE
    }
}
