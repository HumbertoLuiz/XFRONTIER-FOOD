package com.br.nofrontier.food;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.br.nofrontier.food.infrastructure.repository.CustomJpaRepositoryImpl;

@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
@SpringBootApplication(scanBasePackages = {
        "com.br.nofrontier.food.api.v1.controller",
        "com.br.nofrontier.food.core.config",
        "com.br.nofrontier.food.core.email",
        "com.br.nofrontier.food.core.security",
        "com.br.nofrontier.food.core.web",
        "com.br.nofrontier.food.domain.model",
        "com.br.nofrontier.food.domain.repository",
        "com.br.nofrontier.food.domain.service",
        "com.br.nofrontier.food.infrastructure.repository",
        "com.br.nofrontier.food.infrastructure.service"})
public class NofrontierFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(NofrontierFoodApplication.class, args);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

//        var app = new SpringApplication(NofrontierFoodApplication.class);
//        app.addListeners(new Base64ProtocolResolver());
//        app.run(args);
	}

}
