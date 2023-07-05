package br.com.shared.file.cloud.api.domain.dto;

import br.com.shared.file.cloud.api.domain.Upload;

public class UploadDTO {

	private String id;
	
	public UploadDTO() {

	}
	
	public UploadDTO(Upload upload) {
		this(upload.getToken());
	}

	public UploadDTO(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
