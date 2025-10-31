package com.kedu.project.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * ChatFile 엔티티
 * - 채팅방에서 주고받은 파일의 메타데이터를 저장하는 테이블
 * - GCS에 업로드된 파일과 1:1 대응
 */
@Entity
@Table(name = "chat_file")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 채팅방 ID */
    @Column(nullable = false)
    private String roomId;

    /** 실제 업로드된 파일명 (사용자 원본 파일명) */
    @Column(nullable = false)
    private String originalName;

    /** GCS에 저장된 시스템 파일명 (UUID_원본명) */
    @Column(nullable = false, unique = true)
    private String savedName;

    /** 파일 크기 (bytes) */
    @Column(nullable = false)
    private Long size;

    /** MIME 타입 (ex: image/png, application/pdf) */
    private String contentType;

    /** 업로드 시각 */
    private LocalDateTime uploadTime;

    /** 업로더(보낸 사람) ID */
    private String uploaderId;
}
