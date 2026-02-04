package com.example.SpringSecurityFormBased;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SpringSecurityFormBasedApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityFormBasedApplication.class, args);
	}
}