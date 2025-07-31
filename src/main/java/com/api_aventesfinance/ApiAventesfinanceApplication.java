package com.api_aventesfinance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.api_aventesfinance.config.DotenvLoader;

@SpringBootApplication
@ComponentScan(basePackages = {"com.*"})
@EntityScan(basePackages = {"com.api_aventesfinance.model"})
@EnableJpaRepositories(basePackages = {"com.api_aventesfinance.repository"})
@EnableTransactionManagement
@EnableWebMvc
@RestController
@EnableAutoConfiguration
@EnableCaching
@EnableScheduling
@EnableMethodSecurity // importante para as roles
public class ApiAventesfinanceApplication {

	public static void main(String[] args) {
		DotenvLoader.init();
		SpringApplication.run(ApiAventesfinanceApplication.class, args);
	}

}
