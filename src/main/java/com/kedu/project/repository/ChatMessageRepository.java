package com.kedu.project.repository;

import com.kedu.project.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    /**  기존 메시지 조회 : 방 기준 시간 오름차순 */
    List<ChatMessage> findByRoom_RoomIdOrderBySendTimeAsc(String roomId);

    /**  최신 메시지 페이지 조회 (방 목록 미리보기용) */
    @Query("""
        SELECT cm
        FROM ChatMessage cm
        WHERE cm.room.roomId = :roomId
        ORDER BY cm.sendTime DESC
    """)
    Page<ChatMessage> findPageByRoom(@Param("roomId") String roomId, Pageable pageable);

    int countByRoom_RoomIdAndIdGreaterThanAndSenderNot(String roomId, Long id, String sender);
    int countByRoom_RoomIdAndIdGreaterThan(String roomId, Long id);
    void deleteByRoom_RoomId(String roomId);
}
