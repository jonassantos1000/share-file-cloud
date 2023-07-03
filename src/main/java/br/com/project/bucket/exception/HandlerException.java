package br.com.project.bucket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class HandlerException {

	@ExceptionHandler(FileNotFound.class)
	public ResponseEntity<ResponseError> tratarErro404(FileNotFound e, HttpServletRequest request){
		ResponseError error = new ResponseError(e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
}
