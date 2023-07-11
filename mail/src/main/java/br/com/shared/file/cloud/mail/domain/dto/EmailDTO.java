package br.com.shared.file.cloud.mail.domain.dto;

import br.com.shared.file.cloud.mail.domain.enums.Status;

public class EmailDTO {
	
	private Status status;

	public EmailDTO(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
