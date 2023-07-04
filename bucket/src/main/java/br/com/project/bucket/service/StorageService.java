package br.com.project.bucket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.project.bucket.domains.DataFileSave;
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
	
	public DataFileSave saveFile(Directory directory, String id, InfoFile file) {
		return storage.saveFile(directory.getValue(), id, file);
	}
	
	public boolean deleteFileById(String id) {
		boolean response = storage.deleteFileById(id);
		if (!response) {
			throw new FileNotFound("O ID %s não foi localizado!".formatted(id));
		}
		return response;
	}

	public ResponseData findFileById(String id) {
		return storage.getFileById(id);
	}
	
	public List<ResponseData> findDirectory(Directory directory, String id){
		return storage.getFilesInDirectory(directory.getValue(), id);
	}

}
