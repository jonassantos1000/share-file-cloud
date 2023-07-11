package br.com.shared.file.cloud.mail.exception;

public class ResponseError {

	private String error;
	private String path;
	
	public ResponseError(String error, String path) {
		this.error = error;
		this.path = path;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
