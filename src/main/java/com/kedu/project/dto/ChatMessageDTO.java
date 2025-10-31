package com.kedu.project.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ChatMessageDTO {
    private Long messageId;
    private String roomId;
    private String sender;
    private String content;
    private String fileUrl;
    private String type;          // TALK / FILE / ENTER / LEAVE / READ
    private LocalDateTime sendTime;
    private Integer readCount;    // 계산값 (null 가능)
}
