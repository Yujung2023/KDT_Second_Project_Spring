package com.kedu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.MemberDAO;

@Service
public class MemberService {

	@Autowired
	private MemberDAO memberDao;

	public int checkId(String id) {
		System.out.println("id:"+id);
		int value = memberDao.checkId(id);
		System.out.println(value);
		return value;
	}




}
