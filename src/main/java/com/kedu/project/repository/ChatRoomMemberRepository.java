package com.kedu.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kedu.project.entity.ChatRoomMember;
import com.kedu.project.entity.ChatRoomMemberId;

@Repository
public interface ChatRoomMemberRepository
        extends JpaRepository<ChatRoomMember, ChatRoomMemberId> {

    /**  방 인원 수 조회 */
    @Query("""
        SELECT COUNT(c)
        FROM ChatRoomMember c
        WHERE c.room.roomId = :roomId
    """)
    int countMembers(@Param("roomId") String roomId);

    /**  특정 방에 속한 멤버 ID 목록 */
    @Query("""
        SELECT c.member.id
        FROM ChatRoomMember c
        WHERE c.room.roomId = :roomId
    """)
    List<String> findMemberIds(@Param("roomId") String roomId);

    /**  특정 사용자를 방에서 제거 */
    @Modifying
    @Query("""
        DELETE FROM ChatRoomMember c
        WHERE c.room.roomId = :roomId
        AND c.member.id = :memberId
    """)
    void deleteByRoomIdAndMemberId(@Param("roomId") String roomId, @Param("memberId") String memberId);
    
}
