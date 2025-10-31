package com.kedu.project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.kedu.project.entity.ChatFile;

/**
 * ChatFileRepository
 * - 파일 메타데이터 DB 접근 담당
 * - JPA가 자동으로 구현체를 생성
 */
public interface ChatFileRepository extends JpaRepository<ChatFile, Long> {
    /** 특정 채팅방의 파일 목록 최신순 조회 */
    List<ChatFile> findByRoomIdOrderByUploadTimeDesc(String roomId);

    /** 저장명(sysname)으로 파일 검색 */
    ChatFile findBySavedName(String savedName);
}
