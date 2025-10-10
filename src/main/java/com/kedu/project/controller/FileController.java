package com.kedu.project.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

@RestController
@RequestMapping("/files")
public class FileController {


   @Autowired
   private Storage storage;

  
   @Value("${spring.cloud.gcp.bucket}")
   private String bucketName;

   @PostMapping
   public ResponseEntity<Void> upload(String message , MultipartFile[] files) throws Exception{

      System.out.println(message);

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
}
