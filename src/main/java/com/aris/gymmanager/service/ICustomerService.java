package com.aris.gymmanager.service;

import com.aris.gymmanager.dto.CustomerDTO;
import com.aris.gymmanager.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ICustomerService {

    List<Customer> getCustomersByPlanId(int planId);

    Customer save(Customer theCustomer);

    List<CustomerDTO> convertToDTO(List<Customer> customers);

    Customer findCustomerById(int id);

    List<Customer> findAll();

    void deleteCustomerById(int id);

    void updateCustomersActivationState();

    Customer updateCustomer(Customer customer);
}
