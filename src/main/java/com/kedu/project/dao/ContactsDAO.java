package com.kedu.project.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.project.dto.ContactsDTO;
import com.kedu.project.dto.MemberDTO;


@Repository
public class ContactsDAO {

	@Autowired
	private SqlSession mybatis;

	public int insertContacts(ContactsDTO dto) {

		return mybatis.insert("Contacts.insert",dto);
	}

	// 전체 리스트
	public List<ContactsDTO> SelectContactsList(String user_id) {

		return mybatis.selectList("Contacts.SelectContactsList",user_id);
	}

	// 개인 주소록 리스트
//	public List<ContactsDTO> selectSoloList(String type) {
//
//		return mybatis.selectList("Contacts.selectSoloList",type);
//	}
	public List<ContactsDTO> selectSoloList(Map<String ,Object > params) {

		return mybatis.selectList("Contacts.selectSoloList",params);
	}
	
	// 공용 주소록 리스트
	public List<ContactsDTO> selectMultiList(String type) {

		return mybatis.selectList("Contacts.selectMultiList",type);
	}

	//검색
	public List<ContactsDTO> searchName(String name) {

		return mybatis.selectList("Contacts.searchName", name);
	}

	public List<ContactsDTO> searchByNameAndType(Map<String, Object> param) {

		return mybatis.selectList("Contacts.searchByNameAndType", param);
	}

	public int deleteContacts(List<Long> seqList) {

		return mybatis.delete("Contacts.deleteContacts", seqList);
	}

	public void updateContact(Integer seq, Map<String, String> dto) {
		Map<String, Object> params = new HashMap<>();
		params.put("seq", seq);
		params.put("dto", dto);
		mybatis.update("Contacts.updateContacts", params);
	}


	public Object updateContactsTypeMulti(Map<String , Object> param) {

		return mybatis.update("Contacts.updateContactsTypeMulti", param);
	}

	public Object updateContactsTypeSingle(Map<String, Object> param) {

		return mybatis.update("Contacts.updateContactsTypeSingle", param);
	}

	public List<MemberDTO> selectOranizationList() {
		
		return mybatis.selectList("Contacts.selectOranizationList");
	}

	public Object updateOrganizationTypeMulti(Map<String, Object> param) {
		
		return mybatis.update("Contacts.updateOrganizationTypeMulti", param);
	}

	public Object updateOrganizationTypeSingle(Map<String, Object> param) {
		
		return mybatis.update("Contacts.updateOrganizationTypeSingle", param);
	}






}
