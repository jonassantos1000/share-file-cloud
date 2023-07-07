package br.com.shared.file.cloud.api.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.shared.file.cloud.api.domain.Upload;
import br.com.shared.file.cloud.api.domain.enums.Directory;
import br.com.shared.file.cloud.api.domain.vo.StorageFilesVO;
import br.com.shared.file.cloud.api.domain.vo.StorageUploadVO;
import br.com.shared.file.cloud.api.domain.vo.UploadVO;
import br.com.shared.file.cloud.api.http.StorageClient;
import br.com.shared.file.cloud.api.repository.UploadRepository;

@Service
public class UploadService {

	@Autowired
	StorageClient storageClient;

	@Autowired
	UploadRepository repository;

	public Upload saveUpload(List<UploadVO> files) {
		String code = generateId();
		Upload uploads = new Upload(code, saveUploadStorage(code, files));
		return repository.save(uploads);
	}

	public List<StorageFilesVO> findUpload(String token) {
		List<StorageFilesVO> list = storageClient.getUpload(repository.findByToken(token).getStorageId());
		list.forEach(e -> System.out.println(e.getName()));
		return list;
	}

	private StorageUploadVO saveUploadStorage(String idDirectory, List<UploadVO> files) {
		return storageClient.saveUpload(Directory.SHARED.toString(), idDirectory, files);
	}

	private String generateId() {
		return UUID.randomUUID().toString();
	}

}
