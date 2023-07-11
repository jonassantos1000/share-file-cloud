package br.com.shared.file.cloud.mail.exception;

import br.com.shared.file.cloud.mail.domain.enums.Status;

public class ResponseError {

	private String error;
	private String cause;
	private Status status;
	private String path;
	
	public ResponseError(String error, String path, String cause) {
		this.error = error;
		this.cause = cause;
		this.path = path;
		this.status = Status.NAO_ENVIADO;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}
