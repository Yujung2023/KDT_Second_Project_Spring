package com.kedu.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kedu.project.entity.ChatRoom;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {

	/**  로그인한 사용자가 속한 모든 방 반환 (채팅방 목록용) */
	@Query("""
			    SELECT DISTINCT r
			    FROM ChatRoom r
			    JOIN r.members m
			    WHERE m.member.id = :memberId
			    ORDER BY r.lastUpdatedAt DESC
			""")
	List<ChatRoom> findRoomsOf(@Param("memberId") String memberId);

	@Query("""
		    SELECT m.member.id, m.member.name, m.member.rank_code
		    FROM ChatRoomMember m
		    WHERE m.room.roomId = :roomId
		""")
		List<Object[]> findMemberInfoByRoomId(@Param("roomId") String roomId);
 
	
}
