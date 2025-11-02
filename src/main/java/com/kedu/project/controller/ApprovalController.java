package com.kedu.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.dto.ApprovalDTO;
import com.kedu.project.dto.MemberDTO;
import com.kedu.project.service.ApprovalService;
import com.kedu.project.service.MemberService;

@RestController
@RequestMapping("/Eapproval")
public class ApprovalController {
	
	@Autowired
	private ApprovalService approvalservice;
	
	 @Autowired
	    private MemberService memberSerivce;

	
	@GetMapping("/A")
	public ResponseEntity<List<ApprovalDTO>> getAll(){
		System.out.println("일단왔음");
		List<ApprovalDTO> list=approvalservice.selectAll();		
		return ResponseEntity.ok(list);
	}
	
	
	
	@GetMapping("/{status}")
	public ResponseEntity<List<ApprovalDTO>> getByStatus(@PathVariable String status){
	    List<ApprovalDTO> list;
	    String s = status.toUpperCase();

	    switch (s) {
	        // 진행 문서함
	        case "SHOW":
	            list = approvalservice.selectAll().stream()
	                    .filter(doc -> !"TEMP".equals(doc.getStatus()))
	                    .toList();
	            break;
	        case "WAIT":
	        case "CHECKING":
	        case "REJECTED":
	        case "TEMP":
	            list = approvalservice.selectByStatus(s);
	            break;

	        // 문서 보관함
	        case "APPROVED": // 기안 완료
	        case "PASS":
	            list = approvalservice.selectByStatus("APPROVED");
	            break;
	        case "PROCESSING": // 예정
	            list = approvalservice.selectByStatus("PROCESSING");
	            break;

	        default:
	            list = approvalservice.selectByStatus(s);
	    }

	    return ResponseEntity.ok(list);
	}
	
	@PostMapping("/write")
	public ResponseEntity<String> insert(@RequestBody ApprovalDTO dto) {
	    System.out.println(" 결재 등록 요청: " + dto);
	    try {
	        approvalservice.insert(dto);
	        return ResponseEntity.ok("등록 성공");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.internalServerError().body("등록 실패");
	    }
	}
	
	@PostMapping("/tempSave")
	public ResponseEntity<Void> tempSave(@RequestBody ApprovalDTO dto){
		System.out.println("임시저장 할게용");
		dto.setStatus("TEMP");
		approvalservice.saveTemp(dto); //insert or update 자동 처리해줄거야 
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/detail/{seq}")
	public ResponseEntity<ApprovalDTO> detail(@PathVariable("seq") int seq){
		
	    ApprovalDTO dto =  approvalservice.getDetail(seq);
	    if(dto == null){
	        return ResponseEntity.notFound().build();
	    }

	    List<Map<String, Object>> lineData = approvalservice.selectApprovalLine(String.valueOf(seq));

	    List<MemberDTO> approvers = new ArrayList<>();
	    List<MemberDTO> references = new ArrayList<>();

	    for(Map<String, Object> row : lineData){

	        MemberDTO member = new MemberDTO();
	        member.setId((String) row.get("ID"));
	        member.setName((String) row.get("NAME"));
	        member.setRank_code((String) row.get("RANK_CODE"));
	        member.setStatus((String) row.get("STATUS"));

	        Object orderNo = row.get("ORDERNO");
	        member.setOrderNo(orderNo == null ? null : ((Number)orderNo).intValue()); // ✅ 추가된 줄

	        if(orderNo != null) {
	            approvers.add(member);   // 순번 존재 → 결재자
	        } else {
	            references.add(member);  // 순번 없음 → 참조자
	        }
	    }

	    dto.setApprovers(approvers);
	    dto.setReferenceList(references);

	    return ResponseEntity.ok(dto);
	}

	
	
	//임시저장 데이터 불러오기
	@GetMapping("/temp/{writerId}")
	public ResponseEntity<ApprovalDTO> getTemp(@PathVariable String writerId){
		ApprovalDTO dto=approvalservice.getTemp(writerId);
		return ResponseEntity.ok(dto);
	}
	
	//임시저장 
	@GetMapping("/member/{id}")
	public ResponseEntity<MemberDTO> getMember(@PathVariable String id){
	    return ResponseEntity.ok(memberSerivce.findById(id));
	}
	
	
	//승인
	@PostMapping("/approve")
	public ResponseEntity<Void> approve(@RequestBody Map<String, Object> req){
		  String approvalId = req.get("seq").toString();   // ✅ int X, String O
		   String userId = req.get("userId").toString();
		    approvalservice.approve(approvalId, userId);
		    return ResponseEntity.ok().build();
	}
	
	@PostMapping("/reject")
	public ResponseEntity<Void> reject(@RequestBody Map<String, Object> req){
	    String approvalId = req.get("seq").toString();
	    String userId = req.get("userId").toString();
	    String reason = req.get("reason").toString();

	    approvalservice.reject(approvalId, userId, reason);
	    return ResponseEntity.ok().build();
	}
	
	// 내가 지금 결재해야 할 문서 (승인 대기 / WAIT)
	@GetMapping("/my/wait")
	public ResponseEntity<List<ApprovalDTO>> myWait(@RequestParam String userId){
	    System.out.println("🔥 승인 대기 문서 요청: " + userId);
	    return ResponseEntity.ok(approvalservice.getMyWaitList(userId));
	}

	// 앞으로 결재해야 할 문서 (예정)
	@GetMapping("/my/scheduled")
	public ResponseEntity<List<ApprovalDTO>> myScheduled(@RequestParam String userId){
	    System.out.println("🔥 예정 문서 요청: " + userId);
	    return ResponseEntity.ok(approvalservice.getMyScheduledList(userId));
	}
	


	

	
	

	
}