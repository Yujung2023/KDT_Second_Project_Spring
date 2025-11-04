package com.kedu.project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.ContactsDAO;
import com.kedu.project.dto.ContactsDTO;
import com.kedu.project.dto.MemberDTO;

@Service
public class ContactsService {

	@Autowired
	private ContactsDAO dao;

	public int insertContacts(ContactsDTO dto) {

		return	dao.insertContacts(dto);


	}


	public List<ContactsDTO> SelectContactsList(String user_id) {

		return dao.SelectContactsList(user_id);
	}


	public List<ContactsDTO> searchName(String name) {

		return dao.searchName(name);
	}


	public List<ContactsDTO> selectSoloList(String type, String user_id) {

		Map<String, Object> params = new HashMap<>();
		params.put("user_id", user_id);
		params.put("type", "solo");

		return dao.selectSoloList(params);
	}

	public List<ContactsDTO> selectMultiList(String type) {

		return dao.selectMultiList(type);
	}


	public int deleteContacts(List<Long> seqList) {

		return dao.deleteContacts(seqList);
	}



	public void updateContacts(Map<String, String> dto, List<Integer> seqList) {
		for(Integer seq : seqList) {
			dao.updateContact(seq, dto); // seq 기준으로 DB 업데이트
		}

	}

	public List<ContactsDTO> searchByNameAndType(String name, String type) {
		Map<String , Object> param = new HashMap<>();
		param.put("name", name);
		param.put("type" , type);

		return dao.searchByNameAndType(param);
	}

	public Object updateContactsTypeMulti(Map<String, Object> param) {
		return dao.updateContactsTypeMulti(param);
	}


	public Object updateContactsTypeSingle(Map<String, Object> param) {

		return dao.updateContactsTypeSingle(param);
	}


	public List<MemberDTO> selectOranizationList() {

		return dao.selectOranizationList();
	}



	// 조직도 검색
	public List<MemberDTO> searchByOrgName(String name) {
		
		return dao.searchByOrgName(name);
	}

	
	
	// 개인주소록으로 복사
	public Object copyContactsToSolo(String loginId, List<Integer> seqList) {
		Map<String, Object> map = new HashMap<>();
		map.put("user_id", loginId);
		map.put("seqList", seqList);
		return dao.copyContactsToSolo(map);
	}






}
