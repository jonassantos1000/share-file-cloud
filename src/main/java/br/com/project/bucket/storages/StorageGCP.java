package br.com.project.bucket.storages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import br.com.project.bucket.domains.ResponseData;

@Component("storageGCP")
public class StorageGCP implements AbstractStorage {

	@Value("${bucket.name}")
	private String bucketName;

	@Autowired
	private Storage storage;

	@Override
	public String saveFile(String directory, String id, MultipartFile file) {
		String location = getLocation(directory, id, file.getOriginalFilename());

		try {
			BlobId blobId = BlobId.of(bucketName, location);
			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
			storage.create(blobInfo, file.getBytes());
		} catch (IOException e) {
			throw new IllegalArgumentException("Ocorreu um erro ao salvar arquivo");
		}
		return location;
	}

	@Override
	public boolean deleteFile(String directory, String id, String nameFile) {
		boolean response = false;
		try {
			String location = getLocation(directory, id, nameFile);
			BlobId blobId = BlobId.of(bucketName, location);
			response = storage.delete(blobId);
			if (isDirectoryEmpty(directory)) {
				deleteDirectory(directory);
			}
		} catch (Exception e) {
			return false;
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

		Iterable<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix(directoryPrefix)).iterateAll();

		List<ResponseData> fileList = new ArrayList<>();
		blobs.forEach(blob -> fileList.add(new ResponseData(blob.getName().replaceAll(directoryName, StringUtils.EMPTY),
				Base64.encodeBase64String(blob.getContent()), true)));

		return fileList;
	}

	@Override
	public ResponseData getFileByName(String directory, String id, String nameFile) {
		BlobId blobId = BlobId.of(bucketName, getLocation(directory, id, nameFile));
		Blob blob = storage.get(blobId);
		return new ResponseData(blob.getName(), Base64.encodeBase64String(blob.getContent()), true);
	}

}
