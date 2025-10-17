package com.kedu.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.ContactsDTO;
import com.kedu.project.service.ContactsService;


@RestController
@RequestMapping("/contacts")
public class ContactsController {

	@Autowired
	ContactsService CServ;

	@PostMapping // 주소록 추가
	public ResponseEntity<Void> insertContacts(@RequestBody ContactsDTO dto) {

		CServ.insertContacts(dto);

		return ResponseEntity.ok().build();
	}


	@GetMapping // 주소록 리스트 출력
	public ResponseEntity<List<ContactsDTO>> SelectContactsList(
			@RequestParam(required = false) String name, @RequestParam(required = false) String type) {

		List<ContactsDTO> list;

		if (name != null && !name.isEmpty()) { // 안적으면 검색기능 나오게(수정필요)
			list = CServ.searchName(name);
			// 개인주소록
		} else if (type != null && type.equals("solo")) {
			list = CServ.selectSoloList(type);
			// 공유주소록
		} else if (type != null && type.equals("multi")) { 
			list = CServ.selectMultiList(type);

		} else {
			// 기본 전체 리스트
			list = CServ.SelectContactsList(); // type 안보낼경우 전체출력
		}

		return ResponseEntity.ok(list);
	}


	@DeleteMapping // 주소록 삭제
	public ResponseEntity<Void> deleteContacts(@RequestBody Map<String, List<Long>> seqListdata) {
		List<Long> seqList = seqListdata.get("seqList");
		CServ.deleteContacts(seqList);
		return ResponseEntity.ok().build();
	}


	@PutMapping  // 주소록 수정
	public ResponseEntity<Void> updateContactsType(@RequestBody Map<String, Object> body) {

		String type = (String) body.get("type");

		if ("multi".equals(type)) {
			// 공유 주소록으로 이동
			CServ.updateContactsTypeMulti(body);
		} else if ("solo".equals(type)) {
			// 개인 주소록으로 이동
			CServ.updateContactsTypeSingle(body);
		} else {
			// 알 수 없는 type 처리 (옵션)
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().build();
	}

}






