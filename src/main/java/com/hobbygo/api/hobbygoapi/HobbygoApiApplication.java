package com.hobbygo.api.hobbygoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HobbygoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HobbygoApiApplication.class, args);
	}
}
