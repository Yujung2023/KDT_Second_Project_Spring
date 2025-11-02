package com.kedu.project.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.kedu.project.dao.FilesDAO;
import com.kedu.project.dto.FilesDTO;

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

	//답장 및 반복답장 대비용 
	public List<FilesDTO> copyOriginalFiles(List<String> existingFiles, String module_type, int module_seq) {
		List<FilesDTO> copiedFiles = new ArrayList<>();
		// 새 모듈_seq에 이미 연결된 파일 sysname 조회
		List<FilesDTO> existingInNewModule = filesDAO.getFilesList(module_type, module_seq);
		Set<String> existingSysnames = existingInNewModule.stream()
				.map(FilesDTO::getSysname)
				.collect(Collectors.toSet());

		for (String sysname : existingFiles) {
			List<FilesDTO> originals = filesDAO.getFilesBySysname(sysname); // sysname 기준 복수 가능

			for (FilesDTO original : originals) {
				// 중복 체크
				if (!existingSysnames.contains(original.getSysname())) {
					FilesDTO copy = new FilesDTO(
							module_type,
							module_seq,
							original.getSysname(),
							original.getOrgname(),
							original.getContentType()
							);
					filesDAO.uploadFile(copy); // 새 모듈_seq로 DB에 복사
					copiedFiles.add(copy);
					existingSysnames.add(original.getSysname());
				}
			}
		}

		return copiedFiles;
	}




}
