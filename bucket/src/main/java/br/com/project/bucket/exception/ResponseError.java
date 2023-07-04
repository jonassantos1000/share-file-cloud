package br.com.project.bucket.exception;

public class ResponseError {

	public ResponseError(String error, String path) {
		super();
		this.error = error;
		this.path = path;
	}

	private String error;
	private String path;

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
