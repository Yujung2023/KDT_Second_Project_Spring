package com.kedu.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  메신저 전용 간소화 DTO
 * - 채팅방 멤버 표시용 (이름, 직급, 아이디)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSimpleDTO {
    private String memberId;   // ex) user18
    private String name;       // ex) 홍길동
    private String rankName;   // ex) 과장
}
