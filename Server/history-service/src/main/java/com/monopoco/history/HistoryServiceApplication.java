package com.monopoco.history;

import com.monopoco.history.repository.impl.ResourceRepositoryImpl;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@SecurityScheme(
		name = "bearerAuth",
		scheme = "bearer",
		bearerFormat = "JWT",
		type = SecuritySchemeType.HTTP,
		in = SecuritySchemeIn.HEADER
)
@EnableMongoRepositories(repositoryBaseClass = ResourceRepositoryImpl.class)
public class HistoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HistoryServiceApplication.class, args);
	}


}

