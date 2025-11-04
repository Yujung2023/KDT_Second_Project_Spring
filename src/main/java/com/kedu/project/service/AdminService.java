package com.kedu.project.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kedu.project.dao.AdminDAO;
import com.kedu.project.dto.MemberDTO;

@Service
public class AdminService {

	@Autowired
	AdminDAO adminDAO;
	
	public List<Map<String, Object>> getAllAdmin() {
		return  adminDAO.getAllAdmin();
	}
	
	public int addAdmin(String id) {
	    return adminDAO.addAdmin(id);	
	}

	public int deleteAdmin(String id){
		return adminDAO.deleteAdmin(id);
	}
	//test
}
