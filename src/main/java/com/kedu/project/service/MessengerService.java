package com.kedu.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.MessengerDAO;
import com.kedu.project.dto.MemberDTO;

@Service
public class MessengerService {

	@Autowired
	private MessengerDAO dao;

	public List<MemberDTO> getAllMembers() {
		
		return dao.getAllMembers();
	}
	
}
