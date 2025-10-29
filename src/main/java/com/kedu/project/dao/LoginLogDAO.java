package com.kedu.project.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.LoginLogDTO;

@Repository
public class LoginLogDAO {

	@Autowired
	private SqlSession mybatis;

	public List<LoginLogDTO> searchLogs( Map<String, Object> params) {
		return mybatis.selectList("LoginLog.searchLogs",params);
	}

	public void saveLoginLog(LoginLogDTO loginLogDTO) {
		mybatis.insert("LoginLog.saveLog",loginLogDTO);
	}
}
