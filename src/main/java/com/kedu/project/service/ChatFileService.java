package com.kedu.project.service;

import java.util.List;

import com.kedu.project.dto.ChatFileDTO;
import com.kedu.project.entity.ChatFile;

/**
 * ChatFileService
 * ---------------------------------------------------------
 * 파일 업로드/다운로드와 관련된 메타데이터(DB) 로직을 정의하는 서비스 인터페이스.
 * 
 * Controller는 GCS 업로드/다운로드를 직접 처리하고,
 * 이 서비스는 오직 DB(파일 정보 저장, 조회, 삭제)만 책임진다.
 * 
 * 즉, Controller → ChatFileService → Repository 구조를 가진다.
 * ---------------------------------------------------------
 */
public interface ChatFileService {

    /**
     * 파일 메타데이터 저장
     * - GCS 업로드가 성공한 이후, DB에 파일 정보 등록
     * 
     * @param fileDTO 업로드된 파일의 메타데이터
     */
    void saveFile(ChatFileDTO fileDTO);

    /**
     * 특정 채팅방의 파일 목록 조회
     * 
     * @param roomId 채팅방 식별자
     * @return 파일 리스트 (최신순)
     */
    List<ChatFileDTO> getFilesByRoom(String roomId);

    /**
     * 저장명(sysname)으로 단일 파일 조회
     * 
     * @param savedName GCS 저장명 (UUID_파일명)
     * @return 파일 DTO
     */
    ChatFileDTO getFile(String savedName);

    /**
     * 파일 삭제
     * - DB에서 해당 파일 메타데이터 삭제
     * - GCS 파일 삭제는 Controller에서 수행
     * 
     * @param savedName GCS 저장명
     */
    void deleteFile(String savedName);
}
