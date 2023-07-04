package br.com.project.bucket.storages;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import br.com.project.bucket.domains.DataFileSave;
import br.com.project.bucket.domains.InfoFile;
import br.com.project.bucket.domains.ResponseData;
import br.com.project.bucket.exception.FileNotFound;

@Component("storageGCP")
public class StorageGCP implements AbstractStorage {

	@Value("${bucket.name}")
	private String bucketName;

	@Autowired
	private Storage storage;

	@Override
	public DataFileSave saveFile(String directory, String id, InfoFile file) {
		String location = getLocation(directory, id, file.getFileName());

		BlobId blobId = BlobId.of(bucketName, location);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		storage.create(blobInfo, Base64.decodeBase64(file.getBase64()));
		return new DataFileSave(location, file.getFileName());
	}

	@Override
	public boolean deleteFileById(String id) {
		String location = getLocationById(id);
		String directory = Paths.get(location).getParent().toString();
		BlobId blobId = BlobId.of(bucketName, location);
		
		boolean response = storage.delete(blobId);
		if (isDirectoryEmpty(directory)) {
			deleteDirectory(directory);
		}
		return response;
	}

	@Override
	public boolean deleteDirectory(String directory) {
		Iterable<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix("/")).iterateAll();
		blobs.forEach(blob -> blob.delete());

		return true;
	}

	private String getLocation(String directory, String id, String name) {
		return new StringBuilder(directory).append("/").append(id).append("/").append(name).toString();
	}

	private boolean isDirectoryEmpty(String directoryName) {
		String directoryPrefix = directoryName.endsWith("/") ? directoryName : directoryName + "/";
		Iterable<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix(directoryPrefix)).iterateAll();
		for (Blob blob : blobs) {
			if (!blob.getName().equals(directoryPrefix)) {
				return false; // O diretório não está vazio, pois contém outros blobs
			}
		}
		return true;
	}

	@Override
	public List<ResponseData> getFilesInDirectory(String directory, String id) {
		String directoryName = getLocation(directory, id, StringUtils.EMPTY);
		String directoryPrefix = directoryName.endsWith("/") ? directoryName : directoryName + "/";
		
		if(isDirectoryEmpty(directoryPrefix)) {
			throw new IllegalArgumentException("Diretório solicitado esta vazio.");
		}

		Iterable<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix(directoryPrefix)).iterateAll();

		List<ResponseData> fileList = new ArrayList<>();
		blobs.forEach(blob -> fileList.add(new ResponseData(generetedId(blob.getName()) ,Paths.get(blob.getName()).getFileName().toString(),
				Base64.encodeBase64String(blob.getContent()), true)));

		return fileList;
	}

	@Override
	public ResponseData getFileById(String id) {
		String path = getLocationById(id);

		BlobId blobId = BlobId.of(bucketName, path);
		Blob blob = storage.get(blobId);

		if (blob == null) {
			throw new FileNotFound("ID %s não foi localizado!".formatted(id));
		}

		return new ResponseData(generetedId(blob.getName()), Paths.get(blob.getName()).getFileName().toString(),
				Base64.encodeBase64String(blob.getContent()), true);
	}
	
	private String generetedId(String directory) {
		return Base64.encodeBase64String(directory.getBytes(StandardCharsets.UTF_8));
	}
	
	private String getLocationById(String fileId) {
		return new String(Base64.decodeBase64(fileId));
	}

}
