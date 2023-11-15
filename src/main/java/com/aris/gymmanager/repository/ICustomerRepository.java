package com.aris.gymmanager.repository;

import com.aris.gymmanager.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<Customer, Integer>, ICustomerRepositoryCustom{


}
