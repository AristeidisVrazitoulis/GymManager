package com.aris.gymmanager.service;

import com.aris.gymmanager.model.Customer;

import java.util.List;

public interface ICustomerService {

    void save(Customer theCustomer);

    Customer findCustomerById(int id);

    List<Customer> findAll();

    List<Customer> findCustomerByLastName(String lastName);

    void deleteCustomerById(int id);
}
