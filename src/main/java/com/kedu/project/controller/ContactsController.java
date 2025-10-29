package com.kedu.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.kedu.project.dto.MemberDTO;
import com.kedu.project.service.AuthService;
import com.kedu.project.service.ContactsService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/contacts")
public class ContactsController {

    private final AuthService authService;

	@Autowired
	ContactsService CServ;

    ContactsController(AuthService authService) {
        this.authService = authService;
    }

	@PostMapping // 주소록 추가
	public ResponseEntity<String> insertContacts(@RequestBody ContactsDTO dto , HttpServletRequest request) {
		
		String loginId = (String) request.getAttribute("loginID");
		
		try {
			dto.setUser_id(loginId);
			CServ.insertContacts(dto);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("올바르게 입력해주세요!");
		}	
		return ResponseEntity.ok().build();

	}
	@GetMapping // 주소록 리스트 출력
	public ResponseEntity<List<ContactsDTO>> SelectContactsList(
			@RequestParam(required = false) String name, @RequestParam(required = false) String type , HttpServletRequest request) {

		List<ContactsDTO> list;

		if (type != null && (type.equals("solo") || type.equals("multi"))) {
			// type이 있을 경우: solo/multi 필터 적용
			if (name != null && !name.isEmpty()) {
				// 이름 검색 + 타입 필터
				list = CServ.searchByNameAndType(name, type);
			} else {
				// 이름 없이 타입만 필터
				if (type.equals("solo")) {
					String loginId = (String) request.getAttribute("loginID");
					list = CServ.selectSoloList(type,loginId);
				} else {
					list = CServ.selectMultiList(type);
				}
			}
		} else {
			// 타입 없을 경우 전체 검색
			if (name != null && !name.isEmpty()) {
				list = CServ.searchName(name);
			} else {
				String loginId = (String) request.getAttribute("loginID");
				list = new ArrayList<>();
				list.addAll(CServ.selectMultiList(type));
				list.addAll(CServ.selectSoloList(type,loginId));
				  
			}
		}

		return ResponseEntity.ok(list);

	}
	
	@PutMapping  // 주소록 타입 변경
	public ResponseEntity<Void> updateOrganizationType(@RequestBody Map<String, Object> body) {

		String type = (String) body.get("type");

		if ("multi".equals(type)) {
			// 공유 주소록으로 이동
			CServ.updateOrganizationTypeMulti(body);
		} else if ("solo".equals(type)) {
			// 개인 주소록으로 이동
			CServ.updateOrganizationTypeSingle(body);
		} else {
			// 알 수 없는 type 처리 (옵션)
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().build();
	}


	@DeleteMapping // 주소록 삭제
	public ResponseEntity<Void> deleteContacts(@RequestBody Map<String, List<Long>> seqListdata) {
		List<Long> seqList = seqListdata.get("seqList");
		CServ.deleteContacts(seqList);
		return ResponseEntity.ok().build();
	}


	@PutMapping("/update")  
	public ResponseEntity<Void> updateContacts(@RequestBody Map<String, Object> requestData) {
		Map<String, String> dto = (Map<String, String>) requestData.get("dto");  // name, phone, email 등
		List<Integer> seqList = (List<Integer>) requestData.get("seqList");

		CServ.updateContacts(dto, seqList);

		return ResponseEntity.ok().build();
	}

//
//	@PutMapping("/orgType")  // 주소록 타입 변경
//	public ResponseEntity<Void> updateContactsType(@RequestBody Map<String, Object> body) {
//
//		String type = (String) body.get("type");
//
//		if ("multi".equals(type)) {
//			// 공유 주소록으로 이동
//			CServ.updateOrganizationTypeMulti(body);
//		} else if ("solo".equals(type)) {
//			// 개인 주소록으로 이동
//			CServ.updateOrganizationTypeSingle(body);
//		} else {
//			// 알 수 없는 type 처리 (옵션)
//			return ResponseEntity.badRequest().build();
//		}
//		return ResponseEntity.ok().build();
//	}


	
	
	
	@GetMapping("/organization") // 조직도 리스트 출력
	public ResponseEntity<List<MemberDTO>> SelectOranizationList(){
		
		List<MemberDTO> list = CServ.selectOranizationList();
		
		return ResponseEntity.ok(list);
		
	}
	


}






