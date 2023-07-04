package br.com.project.bucket.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.project.bucket.domains.InfoFile;
import br.com.project.bucket.domains.ResponseData;
import br.com.project.bucket.domains.enums.Directory;
import br.com.project.bucket.exception.FileNotFound;
import br.com.project.bucket.storages.AbstractStorage;

@Service
public class StorageService {
	
	@Autowired
	@Qualifier("storageGCP")
	private AbstractStorage storage;
	
	public String saveFile(Directory directory, String id, InfoFile file) {
		return storage.saveFile(directory.getValue(), id, file).replaceAll(directory.getValue(), StringUtils.EMPTY);
	}
	
	public boolean deleteFile(Directory directory, String id, String nameFile) {
		boolean response = storage.deleteFile(directory.getValue(), id, nameFile);
		if (!response) {
			throw new FileNotFound("O arquivo %s não foi localizado!".formatted(nameFile));
		}
		return response;
	}

	public ResponseData findFileByName(Directory directory, String id, String fileName) {
		return storage.getFileByName(directory.getValue(), id, fileName);
	}
	
	public List<ResponseData> findDirectory(Directory directory, String id){
		return storage.getFilesInDirectory(directory.getValue(), id);
	}

}
