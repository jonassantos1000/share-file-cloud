package br.com.project.bucket.storages;

import java.util.List;

import br.com.project.bucket.domains.DataFileSave;
import br.com.project.bucket.domains.InfoFile;
import br.com.project.bucket.domains.ResponseData;

public interface AbstractStorage {
	
	DataFileSave saveFile(String directory, String id, InfoFile file);
	boolean deleteFileById(String id);
	boolean deleteDirectory(String directory);
	ResponseData getFileById(String id);
	List<ResponseData> getFilesInDirectory(String directory, String id);
}
