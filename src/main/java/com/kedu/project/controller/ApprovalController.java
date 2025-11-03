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

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Eapproval")
public class ApprovalController {
	
	@Autowired
	private ApprovalService approvalservice;
	
	 @Autowired
	 private MemberService memberSerivce;

	
	 @GetMapping("/A")
	 public ResponseEntity<List<ApprovalDTO>> getAll(HttpServletRequest request) {
	     String loginId = (String) request.getAttribute("loginID");
	     List<ApprovalDTO> list = approvalservice.getDocsVisibleTo(loginId);
	     return ResponseEntity.ok(list);
	 }
	
	
	 @GetMapping("/{status}")
	 public ResponseEntity<List<ApprovalDTO>> getByStatus(@PathVariable String status,
	                                                      HttpServletRequest request) {

	     String loginId = (String) request.getAttribute("loginID");
	     if (loginId == null) return ResponseEntity.badRequest().build();

	     System.out.println("📌 상태별 문서 조회 요청 by " + loginId + " | status=" + status);

	     // ✅ 로그인 사용자가 볼 수 있는 전체 문서
	     List<ApprovalDTO> list = approvalservice.getDocsVisibleTo(loginId);

	     String s = status.toUpperCase();

	     switch (s) {
	         case "SHOW": // 전체 (임시 제외)
	             list = list.stream()
	                     .filter(doc -> !"TEMP".equals(doc.getStatus()))
	                     .toList();
	             break;

	         case "WAIT":       // 승인 대기 (문서 상태 WAIT)
	             list = list.stream()
	                     .filter(doc -> "WAIT".equals(doc.getStatus()))
	                     .toList();
	             break;

	         case "PROCESSING":   // 진행 중
	             list = list.stream()
	                     .filter(doc -> "PROCESSING".equals(doc.getStatus()))
	                     .toList();
	             break;

	         case "REJECTED":   // 반려
	             list = list.stream()
	                     .filter(doc -> "REJECTED".equals(doc.getStatus()))
	                     .toList();
	             break;

	         case "APPROVED":   // 기안 완료
	             list = list.stream()
	                     .filter(doc -> "APPROVED".equals(doc.getStatus()))
	                     .toList();
	             break;

	         case "PENDING":  // ✅ 예정 문서 (내 차례 X)
	            list = approvalservice.getMyScheduledList(loginId);
	             break;
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
	
	
	
	@GetMapping("/detail/{seq}")
	public ResponseEntity<ApprovalDTO> detail(@PathVariable("seq") int seq, HttpServletRequest request) {

	    String loginId = (String) request.getAttribute("loginID"); // ✅ 로그인 사용자 ID

	    ApprovalDTO dto = approvalservice.getDetail(seq);
	    if (dto == null) {
	        return ResponseEntity.notFound().build();
	    }

	    List<Map<String, Object>> lineData = approvalservice.selectApprovalLine(String.valueOf(seq));

	    List<MemberDTO> approvers = new ArrayList<>();
	    List<MemberDTO> references = new ArrayList<>();

	    String myStatus = null;  // ✅ 로그인한 사람의 개인 상태

	    // ✅ 현재 아직 처리 안된 사람 중 가장 낮은 순번 = 지금 결재할 차례인 사람
	    Integer currentOrder = lineData.stream()
	            .filter(row -> "N".equals(((String) row.get("STATUS")))) // 아직 결재 안함
	            .map(row -> {
	                Object o = row.get("ORDERNO");
	                return (o == null ? null : ((Number) o).intValue());
	            })
	            .filter(o -> o != null)
	            .min(Integer::compareTo)
	            .orElse(null);

	    for (Map<String, Object> row : lineData) {

	        MemberDTO member = new MemberDTO();
	        member.setId((String) row.get("ID"));
	        member.setName((String) row.get("NAME"));
	        member.setRank_code((String) row.get("RANK_CODE"));
	        member.setStatus((String) row.get("STATUS"));

	        Object orderNo = row.get("ORDERNO");
	        Integer order = (orderNo == null ? null : ((Number) orderNo).intValue());
	        member.setOrderNo(order);

	        // ✅ 현재 로그인한 사용자의 상태 판단
	        if (member.getId().equals(loginId)) {
	            String st = member.getStatus(); // N / Y / R

	            if ("Y".equals(st)) myStatus = "APPROVED";       // 승인 완료
	            else if ("R".equals(st)) myStatus = "REJECTED";  // 반려됨
	            else if ("N".equals(st)) {
	                if (order != null && order.equals(currentOrder)) {
	                    myStatus = "WAITING";   // ✅ 지금 결재해야 하는 내 차례
	                } else {
	                    myStatus = "PENDING";   // ✅ 앞으로 내 차례 (예정)
	                }
	            }
	        }

	        if (order != null) approvers.add(member);
	        else references.add(member);
	    }

	    dto.setApprovers(approvers);
	    dto.setReferenceList(references);
	    dto.setMyStatus(myStatus); // ✅ 개인 상태 DTO에 반영

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