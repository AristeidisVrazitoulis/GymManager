package com.aris.gymmanager;

import com.aris.gymmanager.dao.ICustomerDAO;
import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Subscribes;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
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
			queryForLastName(customerDAO);
		};
	}

	private void queryForLastName(ICustomerDAO customerDAO) {
		List<Customer> customers = customerDAO.findByLastName("Vrazitoulis");
		for (Customer cust : customers){
			System.out.println(cust);
		}
	}

	private void readCustomer(ICustomerDAO customerDAO){

		customerDAO.save(new Customer("Bruce", "Wayne"));
		for(Customer customer : customerDAO.findAll())
		{
			System.out.println(customer);
		}

	}
	private void createCustomer (ICustomerDAO customerDAO){

		// create the customer object
		System.out.println("Creating customer object ...");
		Customer cust1 = new Customer("Tony", "Stark");


//		// save the customer object
//		System.out.println("Saving customer object ...");
		customerDAO.save(cust1);

//		// display id of the saved customer
//		System.out.println("Saved customer:"+ tempCustomer);


	}

}
