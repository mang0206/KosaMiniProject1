package com.example.unimeeting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.unimeeting"})
@EnableJpaRepositories(basePackages = {"com.example.unimeeting.repository"})
@EntityScan(basePackages = {"com.example.unimeeting.domain"})
public class UnimeetingApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnimeetingApplication.class, args);
	}

}
