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
@Table(name = "chat_room")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "room_id")
    private String roomId;  // ✅ UUID Primary Key

    private String roomMembers; // ✅ "userA_userB" 로 저장

    private String roomName;
    private String lastMessage;
    private LocalDateTime lastUpdatedAt;
}
