package com.kedu.project.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
import com.kedu.project.dao.FileDAO;
import com.kedu.project.dao.FilesDAO;
import com.kedu.project.dto.FilesDTO;
import com.kedu.project.dto.MailFileDTO;

@Service
public class FilesService {

	 @Autowired
	    private FilesDAO filesDAO;

	    @Autowired
	    private Storage storage;

	    @Value("${spring.cloud.gcp.bucket}")
	    private String bucketName;

	    public List<FilesDTO> uploadFile(String module_type, int module_seq, MultipartFile[] files) throws IOException {
	       
	    	List<FilesDTO> fileList = new ArrayList<>();

	        for (MultipartFile file : files) {
	            if (!file.isEmpty()) {
	                String sysname = module_type + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

	                BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, sysname)
	                        .setContentType(file.getContentType()).build();
	                storage.create(blobInfo, file.getBytes());

	                FilesDTO FilesDTO = new FilesDTO (
	                		module_type, module_seq, sysname, file.getOriginalFilename(),
	                    file.getContentType());
	                filesDAO.uploadFile(FilesDTO);
	                fileList.add(FilesDTO);
	            }
	        }
	        return fileList;
	    }

	    public byte[] downloadFile(String sysname) throws IOException {
	        System.out.println("📦 [Download 요청 sysname] = " + sysname);

	        // 혹시나 공백이나 앞뒤 슬래시가 있을 경우 방지
	        sysname = sysname.trim();
	        if (sysname.startsWith("/")) sysname = sysname.substring(1);

	        // GCS에서 파일 조회
	        Blob blob = storage.get(BlobId.of(bucketName, sysname));

	        if (blob == null || !blob.exists()) {
	            System.err.println("❌ GCS에서 Blob을 찾을 수 없습니다: " + sysname);
	            throw new IOException("GCS 파일을 찾을 수 없습니다: " + sysname);
	        }

	        System.out.println("✅ [Blob 존재] 경로 = " + blob.getName() + " / 타입 = " + blob.getContentType());
	        return blob.getContent();
	    }


	    public List<FilesDTO> getFilesList(String module_type, int module_seq) {
	        return filesDAO.getFilesList(module_type, module_seq);
	    }
	    
	    public boolean deleteFile(String sysname) {
	        boolean removedFromGcs = storage.delete(bucketName, sysname);
	        int removedFromDb = filesDAO.deleteFile(sysname);
	        return removedFromGcs || removedFromDb > 0;
	    }
}
