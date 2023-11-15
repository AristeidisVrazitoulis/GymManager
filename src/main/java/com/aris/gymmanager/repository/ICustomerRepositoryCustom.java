package com.aris.gymmanager.repository;

import com.aris.gymmanager.model.Customer;

import java.util.List;

public interface ICustomerRepositoryCustom {

    List<Customer> findByLastName(String lastName);


}
