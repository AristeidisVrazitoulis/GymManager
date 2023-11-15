package com.aris.gymmanager;

import com.aris.gymmanager.dao.ICustomerDAO;
import com.aris.gymmanager.model.Customer;
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

	@Bean
	public CommandLineRunner commandLineRunner(ICustomerDAO customerDAO){
		return runner -> {
			// createCustomer(customerDAO);
			// readCustomer(customerDAO);
			// queryForLastName(customerDAO);
		};
	}



}
