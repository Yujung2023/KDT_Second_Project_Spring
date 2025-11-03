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
@Table(name = "CHAT_FILE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID") // ✅ 실제 컬럼명과 매핑
    private Long id;

    @Column(name = "ROOM_ID", nullable = false)
    private String roomId;

    @Column(name = "ORIGINAL_NAME", nullable = false)
    private String originalName;

    @Column(name = "SAVED_NAME", nullable = false, unique = true)
    private String savedName;

    @Column(name = "FILE_SIZE", nullable = false)
    private Long size;

    @Column(name = "CONTENT_TYPE")
    private String contentType;

    @Column(name = "UPLOAD_TIME")
    private LocalDateTime uploadTime;

    @Column(name = "UPLOADER_ID")
    private String uploaderId;

    @Column(name = "DOWNLOAD_COUNT")
    private Integer downloadCount;
}
