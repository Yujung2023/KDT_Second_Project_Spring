package com.kedu.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kedu.project.entity.ChatMessageRead;
import com.kedu.project.entity.ChatMessageReadId;

@Repository
public interface ChatMessageReadRepository
        extends JpaRepository<ChatMessageRead, ChatMessageReadId> {

    /** ✅ 읽은 사람 수 계산 → readCount = (총 멤버 수 - 이거) */
    @Query("SELECT COUNT(r) FROM ChatMessageRead r WHERE r.message.id = :messageId")
    int countReaders(@Param("messageId") Long messageId);
    void deleteByMessage_Room_RoomId(String roomId);
}
