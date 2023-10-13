package com.example.unimeeting;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication//(exclude= DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.example.unimeeting", "thymeleaf.exam"})
@MapperScan(value={"com.example.unimeeting.dao"})
public class UnimeetingApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnimeetingApplication.class, args);
	}

}
