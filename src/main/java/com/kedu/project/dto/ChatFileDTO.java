package com.kedu.project.dto;

import lombok.*;
import java.util.Date;

/**
 * ChatFileDTO
 * - 컨트롤러 ↔ 서비스 간 데이터 전달용 DTO
 * - Entity와 거의 동일하지만 JSON 직렬화에 유리한 형태로 사용
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatFileDTO {
    private Long id;
    private String roomId;
    private String originalName;
    private String sysdName;
    private long size;
    private String contentType;
    private Date uploadTime;
    private String uploaderId;
}
