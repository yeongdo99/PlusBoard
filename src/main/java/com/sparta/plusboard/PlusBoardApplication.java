package com.sparta.plusboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PlusBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlusBoardApplication.class, args);
	}

}
