package com.aris.gymmanager.service;

import com.aris.gymmanager.dto.CustomerDTO;
import com.aris.gymmanager.entity.Customer;

import java.util.List;

public interface ICustomerService {

    void save(Customer theCustomer);

    List<CustomerDTO> convertToDTO(List<Customer> customers);

    Customer findCustomerById(int id);

    List<Customer> findAll();

    List<Customer> findCustomerByLastName(String lastName);

    void deleteCustomerById(int id);

    void updateCustomersActivationState();


    // List<Object> getCustomersWIthPlan();
}
