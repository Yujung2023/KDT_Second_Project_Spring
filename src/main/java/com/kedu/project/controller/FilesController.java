package com.kedu.project.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.kedu.project.dto.FilesDTO;
import com.kedu.project.dto.MailFileDTO;
import com.kedu.project.service.FileService;
import com.kedu.project.service.FilesService;

@Controller
@RequestMapping("/files")
public class FilesController {

	@Autowired
	private Storage storage;

	@Autowired
	private FilesService filesService;

	@Value("${spring.cloud.gcp.bucket}")
	private String bucketName;

	@PostMapping ("/upload") // 파일 업로드
	public ResponseEntity<List<FilesDTO>> uploadFile( @RequestParam String module_type , 
			@RequestParam int module_seq ,
			@RequestParam("files") MultipartFile[] files) throws Exception{

		List<FilesDTO> list = filesService.uploadFile(module_type, module_seq, files);
		return ResponseEntity.ok(list);
	}



	@GetMapping("/download")
	public ResponseEntity<byte[]> downloadFile(@RequestParam String sysname) throws Exception {
		byte[] content = filesService.downloadFile(sysname);

		FilesDTO meta = filesService.getFilesList("all", 0).stream()
				.filter(f -> f.getSysname().equals(sysname))
				.findFirst().orElse(null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData(
				"attachment",
				new String((meta != null ? meta.getOrgname() : sysname).getBytes("utf-8"), "ISO-8859-1")
				);

		return new ResponseEntity<>(content, headers, HttpStatus.OK);
	}

	// 파일 목록
	@GetMapping("/fileList")
	public ResponseEntity<List<FilesDTO>> getFilesList( @RequestParam String module_type, @RequestParam int module_seq) {
		return ResponseEntity.ok(filesService.getFilesList(module_type, module_seq));
	}

	// 삭제 (GCS + DB 메타)
	@DeleteMapping("")
	public ResponseEntity<Void> deleteFile(@RequestParam  String sysname) {
		  System.out.println("삭제 요청 sysname: " + sysname);
		boolean ok = filesService.deleteFile(sysname);
		return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}

	// 기존 파일 복사해서 새 모듈_seq에 연결
	@PostMapping("/upload/original")
	public ResponseEntity<Void> copyOriginalFiles(
	        @RequestParam String module_type, 
	        @RequestParam int module_seq, 
	        @RequestParam("existingFiles") List<String> existingFiles) {

	    if (existingFiles == null || existingFiles.isEmpty()) {
	        return ResponseEntity.badRequest().build();
	    }

	    filesService.copyOriginalFiles(existingFiles, module_type, module_seq);
	    return ResponseEntity.ok().build();
	}
	
}
