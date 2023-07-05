package br.com.project.bucket.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerException {

	@ExceptionHandler(FileNotFound.class)
	public ResponseEntity<ResponseError> tratarErro404(FileNotFound e, HttpServletRequest request){
		ResponseError error = new ResponseError(e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ResponseError> tratarErro404(IllegalArgumentException e, HttpServletRequest request){
		ResponseError error = new ResponseError(e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
}
