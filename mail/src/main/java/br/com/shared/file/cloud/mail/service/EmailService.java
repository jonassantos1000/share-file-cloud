package br.com.shared.file.cloud.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.shared.file.cloud.mail.domain.Email;
import br.com.shared.file.cloud.mail.domain.dto.EmailDTO;
import br.com.shared.file.cloud.mail.domain.enums.Status;
import br.com.shared.file.cloud.mail.exception.EmailException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String addressEmail;
	
	public EmailDTO requestSend(Email email) {
		sendEmail(email.getSubject(), email.getAddressEmail(), email.getContent());
		return new EmailDTO(Status.ENVIADO);
	}

	private void sendEmail(String subject, String emailTarget, String content) {
		try {
			MimeMessage mail = mailSender.createMimeMessage();

			MimeMessageHelper message = new MimeMessageHelper(mail);
			message.setSubject(subject);
			message.setText(content, true);
			message.setTo(emailTarget);
			message.setFrom(addressEmail);

			mailSender.send(mail);

		} catch (Exception e) {
			e.printStackTrace();
			throw new EmailException("Não foi possivel realizar o envio do e-mail.");
		}
	}
}
