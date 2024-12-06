package com.example.ReviewsInTheStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.contract_first", "com.example.ReviewsInTheStore"})
public class Main {


	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
