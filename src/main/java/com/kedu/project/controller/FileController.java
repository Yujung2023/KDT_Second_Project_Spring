package com.kedu.project.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.kedu.project.dto.MailFileDTO;
import com.kedu.project.service.FileService;

@RestController
@RequestMapping("/file")
public class FileController {

	// 아무거나
	//아무거나
	@Autowired
	private Storage storage;

	@Autowired
	private FileService FServ;


	@Value("${spring.cloud.gcp.bucket}")
	private String bucketName;

	@PostMapping
	public ResponseEntity<Void> upload(String message , MultipartFile[] files) throws Exception{

		System.out.println(message);
		if (files != null) {// null 체크 추가
			for( MultipartFile file : files) {

				if(!file.isEmpty()) {   
					String sysname = UUID.randomUUID() + "_" + file.getOriginalFilename();

					BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, sysname))
							.setContentType(file.getContentType()).build();

					try(InputStream is = file.getInputStream()){
						storage.createFrom(blobInfo, is);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<List<String>> list() {

		List<String> fileNames = new ArrayList<>();

		Page<Blob> blobs = storage.list(bucketName);
		for(Blob b : blobs.iterateAll()) {
			fileNames.add(b.getName());
		}

		return ResponseEntity.ok(fileNames);
	}



	@GetMapping("/{sysname}")
	public ResponseEntity<byte[]> download(@PathVariable String sysname) throws Exception {

		System.out.println(sysname);
		Blob blob = storage.get(bucketName,sysname);
		byte[] content = blob.getContent();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // 브라우저 없이 다운로드 링크로
		headers.setContentDispositionFormData("attachment",new String(sysname.getBytes("utf8"),"ISO-8859-1"));

		return new ResponseEntity<>(content,headers,HttpStatus.OK);
	}

	
	
	//메일별 파일 업로드
	@PostMapping("/mailSeq")
	public ResponseEntity<Void> insertMailFileUpload(@RequestParam Long mailSeq, MultipartFile[] files) throws IOException {
		List<MailFileDTO> fileList = new ArrayList<>();
		for(MultipartFile file : files) {
		    String sysname = UUID.randomUUID() + "_" + file.getOriginalFilename();
		    storage.create(BlobInfo.newBuilder(bucketName, sysname).build(), file.getBytes());
		    fileList.add(new MailFileDTO(mailSeq, sysname, file.getOriginalFilename()));
		}

		FServ.insertMailFileUpload(fileList);
	    return ResponseEntity.ok().build(); 

	}


	//메일별 파일 다운로드
	@GetMapping("/download")
	public ResponseEntity<byte[]> downloadFileBySeq(@RequestParam Long mailSeq,@RequestParam String sysname) throws IOException {
		
	    MailFileDTO file = FServ.downloadFileBySeq(mailSeq, sysname); // DB에서 파일 메타 정보 조회
	    if (file == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }

	    Blob blob = storage.get(bucketName, file.getSysname()); // GCP Storage에서 파일 조회
	    byte[] content = blob.getContent();

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment",
	            new String(file.getOrgname().getBytes("utf-8"), "ISO-8859-1"));

	    return new ResponseEntity<>(content, headers, HttpStatus.OK);
	}

	// 메일별 파일 조회
	@GetMapping("/mail")
	public ResponseEntity<List<MailFileDTO>> getMailFiles(@RequestParam Long mailSeq) {
	    List<MailFileDTO> fileList = FServ.getMailFileList(mailSeq); // DB에서 mailSeq별 파일 조회
	    return ResponseEntity.ok(fileList);
	}

}
