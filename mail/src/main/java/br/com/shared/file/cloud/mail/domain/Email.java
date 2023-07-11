package br.com.shared.file.cloud.mail.domain;

import br.com.shared.file.cloud.mail.domain.vo.EmailVO;

public class Email {

	private String addressEmail;
	private String subject;
	private String content;

	public Email() {

	}
	
	public Email(EmailVO emailVO) {
		this.addressEmail = emailVO.getEmail();
		this.subject = emailVO.getSubject();
		this.content = emailVO.getContent();
	}

	public Email(String email, String subject, String content) {
		this.addressEmail = email;
		this.subject = subject;
		this.content = content;
	}


	public String getAddressEmail() {
		return addressEmail;
	}

	public void setAddressEmail(String addressEmail) {
		this.addressEmail = addressEmail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
