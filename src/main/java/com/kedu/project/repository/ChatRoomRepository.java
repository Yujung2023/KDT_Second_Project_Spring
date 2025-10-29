package com.kedu.project.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kedu.project.entity.ChatRoom;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {

    //  1:1 채팅방 정확히 조회
    Optional<ChatRoom> findByRoomMembers(String roomMembers);

    //  로그인 사용자 포함된 모든 방 조회 (채팅 리스트 화면용)
    List<ChatRoom> findByRoomMembersContainingOrderByLastUpdatedAtDesc(String memberId);
    List<ChatRoom> findByRoomMembersContaining(String userId);
}
