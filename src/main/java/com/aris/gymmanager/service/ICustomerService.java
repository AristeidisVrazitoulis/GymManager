package com.aris.gymmanager.service;

import com.aris.gymmanager.model.Customer;

import java.util.List;

public interface ICustomerService {

    void save(Customer theCustomer);

    Customer findCustomerById(int id);

    List<Customer> findAll();

    List<Customer> findByLastName(String lastName);

    void update(Customer theCustomer);

    void deleteCustomerById(int id);
}
