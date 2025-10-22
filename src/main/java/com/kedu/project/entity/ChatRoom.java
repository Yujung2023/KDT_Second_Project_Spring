
package com.kedu.project.entity;

import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "chatRoom")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 방 PK

    private String roomName;    // 방 이름 (1:1이면 null 가능)
    private LocalDateTime createdAt;
}
