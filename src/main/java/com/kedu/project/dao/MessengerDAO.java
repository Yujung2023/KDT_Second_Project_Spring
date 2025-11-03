package com.kedu.project.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.MemberDTO;

@Repository
public class MessengerDAO {

    @Autowired
    private SqlSession mybatis;

    /** 전체 멤버 목록 조회 */
    public List<MemberDTO> getAllMembers() {
        return mybatis.selectList("Messenger.getAllMembers");
    }

    /** 근무 상태 업데이트 */
    public int updateWorkStatus(String id, String workStatus) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("work_status", workStatus); // ✅ 스네이크 케이스 키

        int result = mybatis.update("Messenger.updateWorkStatus", params);
        System.out.println("🔍 updateWorkStatus() 실행 결과: " + result + "건 / id=" + id + " / work_status=" + workStatus);
        return result;
    }
}
