package br.com.shared.file.cloud.mail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringDocConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Serviço para envio de e-mail")
						.description("API Rest contendo funcionalidades para realizar envios de e-mail")
						.contact(new Contact()
                                .name("Jonas Silva")
                                .email("jonasvale3@hotmail.com"))
				.license(new License()
						.name("Apache 2.0")
						.url("http://mail-ms/api/licenca")));
	}
}
