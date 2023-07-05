package br.com.shared.file.cloud.api.domain.vo;

public class UploadVO {

	private String fileName;
	private String base64;

	public UploadVO() {
	}

	public UploadVO(String fileName, String base64) {
		this.fileName = fileName;
		this.base64 = base64;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

}
