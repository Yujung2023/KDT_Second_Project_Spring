package com.kedu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.AuthDAO;

@Service
public class AuthService {

	@Autowired
	AuthDAO authDao;
	
	public int check(String id){
		return authDao.check(id);
	}
}
