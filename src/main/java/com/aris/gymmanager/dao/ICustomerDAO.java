package com.aris.gymmanager.dao;

import com.aris.gymmanager.entity.Customer;

import java.util.List;

public interface ICustomerDAO {

    void save(Customer theCustomer);

    Customer findCustomerById(int id);

    List<Customer> findAll();

    List<Customer> findByLastName(String lastName);

    void update(Customer theCustomer);

    void deleteCustomerById(int id);
}
