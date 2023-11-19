package com.aris.gymmanager.repository;

import com.aris.gymmanager.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICustomerRepository extends JpaRepository<Customer, Integer>{

    public List<Customer> findCustomerByLastName(String lastName);
}
