package com.aris.gymmanager;

import com.aris.gymmanager.dao.ICustomerDAO;
import com.aris.gymmanager.entity.Customer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GymManagerApplication {

	public static void main(String[] args) {

		SpringApplication.run(GymManagerApplication.class, args);

	}

	@Bean
	public CommandLineRunner commandLineRunner(ICustomerDAO customerDAO){
		return runner -> {
			createCustomer(customerDAO);
		};
	}

	private void createCustomer (ICustomerDAO customerDAO){

		// create the customer object
		System.out.println("Creating customer object ...");
		Customer tempCustomer = new Customer("Aris", "Vrazitoulis");

		// save the customer object
		System.out.println("Saving customer object ...");
		customerDAO.save(tempCustomer);
		// display id of the saved customer
		System.out.println("Saved customer:"+ tempCustomer);

	}

}
