package br.com.shared.file.cloud.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.shared.file.cloud.mail.domain.Email;
import br.com.shared.file.cloud.mail.domain.dto.EmailDTO;
import br.com.shared.file.cloud.mail.domain.vo.EmailVO;
import br.com.shared.file.cloud.mail.service.EmailService;

@RequestMapping("/email")
public class EmailController {

	@Autowired
	private EmailService service;
	
	@PostMapping()
	public ResponseEntity<EmailDTO> postEmail(EmailVO emailVO){
		Email email = new Email(emailVO);
		return ResponseEntity.ok(service.requestSend(email));
	}
	
}
