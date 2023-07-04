package br.com.project.bucket.exception;

public class FileNotFound extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileNotFound(String message) {
		super(message);
	}
	
}
