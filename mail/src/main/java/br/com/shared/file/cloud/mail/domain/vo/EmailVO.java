package br.com.shared.file.cloud.mail.domain.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailVO {

	@NotBlank
	private String subject;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String content;

	public EmailVO() {

	}

	public EmailVO(String subject, String email, String content) {
		this.subject = subject;
		this.email = email;
		this.content = content;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
