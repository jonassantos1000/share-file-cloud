package br.com.project.bucket.storages;

import java.util.List;

import br.com.project.bucket.domains.InfoFile;
import br.com.project.bucket.domains.ResponseData;

public interface AbstractStorage {
	
	String saveFile(String directory, String id, InfoFile file);
	boolean deleteFile(String directory, String id, String nameFile);
	boolean deleteDirectory(String directory);
	ResponseData getFileByName(String directory, String id, String nameFile);
	List<ResponseData> getFilesInDirectory(String directory, String id);
}
