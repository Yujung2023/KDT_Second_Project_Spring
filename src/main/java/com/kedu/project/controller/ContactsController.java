package com.kedu.project.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("올바른 값 또는 다른 아이디와 중복없도록 입력해주세요!");
		}	
		return ResponseEntity.ok().build();

	}

	//주소록 리스트 출력
	@GetMapping
	public ResponseEntity<List<ContactsDTO>> SelectContactsList(@RequestParam(required = false) String name, @RequestParam(required = false) String type, 
	        HttpServletRequest request) {

	    String loginId = (String) request.getAttribute("loginID");
	    List<ContactsDTO> list;

	    if (type != null && (type.equals("solo") || type.equals("multi"))) {
	        // type이 있을 경우: solo/multi 필터 
	        if (name != null && !name.isEmpty()) {
	            // 검색 후에도 중복 제거
	            List<ContactsDTO> searchList = CServ.searchByNameAndType(name, type);
	            Map<String, ContactsDTO> combinedMap = new LinkedHashMap<>();
	            
	            for (ContactsDTO dto : searchList) {
	                if ("multi".equals(type)) {
	                    combinedMap.put(dto.getEmail() + "_multi", dto); // 멀티는 key에 _multi
	                } else {
	                	 if ("solo".equals(type) && !loginId.equals(dto.getUser_id())) continue;  // 개인주소록은 내 아이디기준으로만
	                    combinedMap.put(dto.getEmail() + "_" + dto.getUser_id(), dto); // 개인 주소록은 user_id 포함
	                }
	            }

	            list = new ArrayList<>(combinedMap.values());
	        } else { // 개인주소록 또는 공용주소록
	            if (type.equals("solo")) {
	                List<ContactsDTO> soloList = CServ.selectSoloList(type, loginId);
	                Map<String, ContactsDTO> combinedMap = new LinkedHashMap<>();
	                for (ContactsDTO dto : soloList) {
	                    combinedMap.put(dto.getEmail() + "_" + dto.getUser_id(), dto); // 개인 주소록은 user_id 포함
	                }

	                list = new ArrayList<>(combinedMap.values());
//	              list = CServ.selectSoloList(type, loginId);
	            } else { // 공용주소록
	                List<ContactsDTO> multiList = CServ.selectMultiList(type);
	                Map<String, ContactsDTO> combinedMap = new LinkedHashMap<>();

	                for (ContactsDTO dto : multiList) {
	                    combinedMap.put(dto.getEmail() + "_multi", dto); // 멀티는 key에 _multi
	                }
	                list = new ArrayList<>(combinedMap.values());
	            }
	        }
	    } else {
	        // 타입 없을 경우 전체 검색
	        if (name != null && !name.isEmpty()) {
	            // 검색 후에도 중복 제거
	            List<ContactsDTO> searchList = CServ.searchName(name);
	            Map<String, ContactsDTO> combinedMap = new LinkedHashMap<>();

	            for (ContactsDTO dto : searchList) {
	                if ("multi".equals(dto.getType())) {
	                    combinedMap.put(dto.getEmail() + "_multi", dto); // 멀티는 key에 _multi
	                } else {
	                	  if (!loginId.equals(dto.getUser_id())) continue; // 개인주소록은 내 아이디기준으로만
	                    combinedMap.put(dto.getEmail() + "_" + dto.getUser_id(), dto); // 개인 주소록은 user_id 포함
	                }
	            }

	            list = new ArrayList<>(combinedMap.values());
	        } else {
	            List<ContactsDTO> multiList = CServ.selectMultiList(type);
	            List<ContactsDTO> soloList = CServ.selectSoloList(type, loginId);

	            // 중복 제거: 이메일+user_id 기준
	            Map<String, ContactsDTO> combinedMap = new LinkedHashMap<>();

	            for (ContactsDTO dto : multiList) {
	                combinedMap.put(dto.getEmail() + "_multi", dto); // 멀티는 key에 _multi
	            }
	            for (ContactsDTO dto : soloList) {
	                combinedMap.put(dto.getEmail() + "_" + dto.getUser_id(), dto); // 개인 주소록은 user_id 포함
	            }

	            list = new ArrayList<>(combinedMap.values());
	        }
	    }

	    return ResponseEntity.ok(list);
	}



	@PutMapping  // 주소록 타입 변경
	public ResponseEntity<Void> updateContactsType(@RequestBody Map<String, Object> body ,  HttpServletRequest request) {

		String loginId = (String) request.getAttribute("loginID");
		String type = (String) body.get("type");
		body.put("user_id", loginId);
		if ("multi".equals(type)) {
			// 공유 주소록으로 이동
			CServ.updateContactsTypeMulti(body);
		} 
		else if ("solo".equals(type)) {
			// 개인 주소록으로 이동

			CServ.updateContactsTypeSingle(body);

		} 
		else {
			// 알 수 없는 type 처리 (옵션)
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().build();
	}


	// 개인주소록으로 복사
	@PutMapping("/toSoloCopy")
	public ResponseEntity<Void> copyToSoloContacts(@RequestBody Map<String, Object> body, HttpServletRequest request) {
		String loginId = (String) request.getAttribute("loginID");
		List<Integer> seqList = (List<Integer>) body.get("seqList");

		if (loginId == null || seqList == null || seqList.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		CServ.copyContactsToSolo(loginId, seqList);
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




	@GetMapping("/organization") // 조직도 리스트 출력
	public ResponseEntity<List<MemberDTO>> SelectOranizationList(@RequestParam(required = false) String name){

		List<MemberDTO> list;

		if (name != null) {
			//조직도 이름으로 검색
			list = CServ.searchByOrgName(name);
		}else {
			list = CServ.selectOranizationList();
		}


		return ResponseEntity.ok(list);

	}



}






