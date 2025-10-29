package com.kedu.project.repository;

import com.kedu.project.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    /** ✅ 기존 메세지 조회: roomId 기준 오름차순(읽기위해) */
    List<ChatMessage> findByRoom_RoomIdOrderBySendTimeAsc(String roomId);

    /** ✅ 내가 읽지 않은 메시지 수 조회 예정 (읽음 기능 확장 대비) */
    List<ChatMessage> findByRoom_RoomIdAndSenderNotAndReadCount(String roomId, String sender, int readCount);
}
