package br.com.project.bucket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BucketApplication {

	public static void main(String[] args) {
		SpringApplication.run(BucketApplication.class, args);
	}

}
