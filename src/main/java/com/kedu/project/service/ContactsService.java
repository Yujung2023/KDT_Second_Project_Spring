package com.kedu.project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.ContactsDAO;
import com.kedu.project.dto.ContactsDTO;

@Service
public class ContactsService {

	@Autowired
	private ContactsDAO dao;
	
	public int insertContacts(ContactsDTO dto) {
		
	 return	dao.insertContacts(dto);
		
		
	}


	public List<ContactsDTO> SelectContactsList() {
		
		return dao.SelectContactsList();
	}


	public List<ContactsDTO> searchName(String name) {
		
		return dao.searchName(name);
	}


	public List<ContactsDTO> selectSoloList(String type) {
		
		return dao.selectSoloList(type);
	}


	public List<ContactsDTO> selectMultiList(String type) {
	
		return dao.selectMultiList(type);
	}


	public int deleteContacts(List<Long> seqList) {
		
		return dao.deleteContacts(seqList);
	}



	public Object updateContactsTypeMulti(Map<String, Object> param) {
		return dao.updateContactsTypeMulti(param);
	}


	public Object updateContactsTypeSingle(Map<String, Object> param) {
		
		return dao.updateContactsTypeSingle(param);
	}


}
