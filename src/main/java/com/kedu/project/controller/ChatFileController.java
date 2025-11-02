//package com.kedu.project.controller;
//
//import java.io.InputStream;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.util.*;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.google.cloud.storage.*;
//import com.kedu.project.dto.ChatFileDTO;
//import com.kedu.project.service.ChatFileService;
//
//@RestController
//@RequestMapping("/api/chat")
//public class ChatFileController {
//
//    @Autowired
//    private Storage storage; // GCS 스토리지 빈
//
//    @Value("${spring.cloud.gcp.bucket}")
//    private String bucketName;
//
//    @Autowired
//    private ChatFileService chatFileService; // 파일 정보 DB 기록용 서비스
//
//    /**
//     * 파일 업로드
//     * - 채팅방(roomId) 기준으로 파일 업로드 처리
//     * - GCS에 업로드 후 DB에 파일 메타데이터 저장
//     */
//    @PostMapping("/upload")
//    public ResponseEntity<?> uploadChatFile(
//            @RequestPart("file") MultipartFile file,
//            @RequestParam String roomId) {
//
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body("파일이 비어 있습니다.");
//        }
//
//        try (InputStream inputStream = file.getInputStream()) {
//
//            // 파일명 중복 방지를 위한 UUID 추가
//            String sysname = UUID.randomUUID() + "_" + file.getOriginalFilename();
//
//            // Blob 메타 생성
//            BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, sysname))
//                    .setContentType(file.getContentType())
//                    .build();
//
//            // GCS에 파일 업로드
//            storage.createFrom(blobInfo, inputStream);
//
//            // DTO 구성 및 DB 저장
//            ChatFileDTO dto = new ChatFileDTO();
//            dto.setRoomId(roomId);
//            dto.setOriginalName(file.getOriginalFilename());
//            dto.setSysdName(sysname);
//            dto.setSize(file.getSize());
//            dto.setUploadTime(new Date());
//
//            chatFileService.saveFile(dto);
//
//            return ResponseEntity.ok(dto);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().body("파일 업로드 실패: " + e.getMessage());
//        }
//    }
//
//    /**
//     * 채팅방별 파일 목록 조회
//     * - roomId 기준으로 DB 조회
//     */
//    @GetMapping("/files")
//    public ResponseEntity<List<ChatFileDTO>> getFilesByRoom(@RequestParam String roomId) {
//        try {
//            List<ChatFileDTO> files = chatFileService.getFilesByRoom(roomId);
//            return ResponseEntity.ok(files);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().build();
//        }
//    }
//
//    /**
//     * 파일 다운로드
//     * - GCS에서 Blob을 가져와 스트림으로 반환
//     * - Content-Disposition: attachment
//     */
//    @GetMapping("/download/{savedName}")
//    public ResponseEntity<?> downloadFile(@PathVariable String savedName) {
//        try {
//            Blob blob = storage.get(BlobId.of(bucketName, savedName));
//            if (blob == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("파일을 찾을 수 없습니다.");
//            }
//
//            byte[] content = blob.getContent();
//
//            String encodedName = URLEncoder.encode(savedName, StandardCharsets.UTF_8);
//
//            return ResponseEntity.ok()
//                    .contentType(MediaType.parseMediaType(blob.getContentType()))
//                    .header(HttpHeaders.CONTENT_DISPOSITION,
//                            "attachment; filename=\"" + encodedName + "\"")
//                    .body(content);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().body("파일 다운로드 실패: " + e.getMessage());
//        }
//    }
//
//    /**
//     * 파일 삭제 (선택 사항)
//     * - 필요시 클라이언트에서 삭제 요청 시 사용
//     */
//    @DeleteMapping("/file/{savedName}")
//    public ResponseEntity<String> deleteFile(@PathVariable String savedName) {
//        try {
//            boolean deleted = storage.delete(BlobId.of(bucketName, savedName));
//            if (deleted) {
//                chatFileService.deleteFile(savedName);
//                return ResponseEntity.ok("파일 삭제 성공");
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("파일을 찾을 수 없습니다.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().body("파일 삭제 실패");
//        }
//    }
//}
