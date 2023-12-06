package com.aris.gymmanager.repository;

import com.aris.gymmanager.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICustomerRepository extends JpaRepository<Customer, Integer>{



    List<Customer> findByPlanId(int id);




}
