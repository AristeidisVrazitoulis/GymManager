package com.aris.gymmanager;

import com.aris.gymmanager.service.ICustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class GymManagerApplication {

	public static void main(String[] args) {

		SpringApplication.run(GymManagerApplication.class, args);

	}


	// for testing
	@Bean
	public CommandLineRunner commandLineRunner(){
		return runner -> {

		};
	}



}
