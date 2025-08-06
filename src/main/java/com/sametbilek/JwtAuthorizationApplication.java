package com.sametbilek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) // YENİ HALİ BU ŞEKİLDE OLMALI
//@SpringBootApplication
public class JwtAuthorizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthorizationApplication.class, args);
	}

}
