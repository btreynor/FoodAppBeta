package com.btreynor.foodappbeta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan(basePackages = {"com.btreynor.foodappbeta.model.*"})
@ComponentScan(basePackages = {"com.btreynor.foodappbeta.exception.*"})
@EnableJpaRepositories(basePackages = {"com.btreynor.foodappbeta.repository.*"})
public class FoodappbetaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodappbetaApplication.class, args);
	}

}
