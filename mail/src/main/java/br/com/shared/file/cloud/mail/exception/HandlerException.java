package br.com.shared.file.cloud.mail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;


@RestControllerAdvice
public class HandlerException {

	@ExceptionHandler(EmailException.class)
	public ResponseEntity<ResponseError> tratarErro404(EmailException e, HttpServletRequest request){
		ResponseError error = new ResponseError(e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
