package com.kedu.project.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name = "CHAT_ROOM")
public class ChatRoom {

    @Id
    @Column(name = "ROOM_ID", length = 36)
    private String roomId;  // UUID

    @Column(name = "ROOM_NAME")
    private String roomName;

    @Column(name = "LAST_MESSAGE")
    private String lastMessage;

    @Column(name = "LAST_UPDATED_AT")
    private LocalDateTime lastUpdatedAt;

    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    //  단체방 멤버 매핑
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference 
    private List<ChatRoomMember> members = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (lastUpdatedAt == null) lastUpdatedAt = LocalDateTime.now();
    }
    
}
