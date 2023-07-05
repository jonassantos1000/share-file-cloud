package br.com.project.bucket.service;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
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
		boolean response = storage.deleteFileById(decodeId(id));
		if (!response) {
			throw new FileNotFound("O ID %s não foi localizado!".formatted(id));
		}
		return response;
	}

	public ResponseData findFileById(String id) {
		return storage.getFileById(decodeId(id));
	}

	public List<ResponseData> findDirectory(Directory directory, String id) {
		return storage.getFilesInDirectory(directory.getValue(), id);
	}
	
	public List<ResponseData> findDirectory(String idEncode) {
		String path = decodeId(idEncode);
		if (!path.contains("/")) {
			throw new IllegalArgumentException("Diretório informado é invalido");
		}
		
		String[] directory = path.split("/");
		return storage.getFilesInDirectory(directory[0], directory[1]);
	}

	public DataFileSave saveFileAll(Directory directory, String directoryId, List<InfoFile> files) {
		files.forEach(f -> saveFile(directory, directoryId, f));
		
		return new DataFileSave(storage.getLocation(directory.getValue(), directoryId, StringUtils.EMPTY));
	}

	private String decodeId(String fileId) {
		return new String(Base64.decodeBase64(fileId));
	}

}
