package com.kedu.project.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LeaveRequestDAO {
    int selectRemainLeave(String memberId);
}