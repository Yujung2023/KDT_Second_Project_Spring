//package com.kedu.project.service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.kedu.project.dto.ChatFileDTO;
//import com.kedu.project.entity.ChatFile;
//import com.kedu.project.repository.ChatFileRepository;
//
///**
// * ChatFileServiceImpl
// * - DB 저장, 조회, 삭제 담당
// * - GCS 업로드/다운로드는 Controller에서 수행
// */
//@Service
//public class ChatFileServiceImpl implements ChatFileService {
//
//    @Autowired
//    private ChatFileRepository repository;
//
//    /** 파일 메타데이터 저장 */
//    @Override
//    @Transactional
//    public void saveFile(ChatFileDTO dto) {
//        ChatFile entity = ChatFile.builder()
//                .roomId(dto.getRoomId())
//                .originalName(dto.getOriginalName())
//                .savedName(dto.getSysdName())
//                .size(dto.getSize())
//                .contentType(dto.getContentType())
//                .uploadTime(LocalDateTime.now())
//                .uploaderId(dto.getUploaderId())
//                .build();
//        repository.save(entity);
//    }
//
//    /** 특정 채팅방의 파일 목록 조회 */
//    @Override
//    public List<ChatFileDTO> getFilesByRoom(String roomId) {
//        return repository.findByRoomIdOrderByUploadTimeDesc(roomId)
//                .stream()
//                .map(f -> ChatFileDTO.builder()
//                        .id(f.getId())
//                        .roomId(f.getRoomId())
//                        .originalName(f.getOriginalName())
//                        .sysdName(f.getSavedName())
//                        .size(f.getSize())
//                        .contentType(f.getContentType())
//                        .uploadTime(java.sql.Timestamp.valueOf(f.getUploadTime()))
//                        .uploaderId(f.getUploaderId())
//                        .build())
//                .collect(Collectors.toList());
//    }
//
//    /** sysname으로 파일 검색 */
//    @Override
//    public ChatFileDTO getFile(String savedName) {
//        ChatFile f = repository.findBySavedName(savedName);
//        if (f == null) return null;
//
//        return ChatFileDTO.builder()
//                .id(f.getId())
//                .roomId(f.getRoomId())
//                .originalName(f.getOriginalName())
//                .sysdName(f.getSavedName())
//                .size(f.getSize())
//                .contentType(f.getContentType())
//                .uploadTime(java.sql.Timestamp.valueOf(f.getUploadTime()))
//                .uploaderId(f.getUploaderId())
//                .build();
//    }
//
//    /** 파일 삭제 */
//    @Override
//    @Transactional
//    public void deleteFile(String savedName) {
//        ChatFile file = repository.findBySavedName(savedName);
//        if (file != null) repository.delete(file);
//    }
//}
