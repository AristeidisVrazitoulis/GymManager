package com.aris.gymmanager.repository;

import com.aris.gymmanager.dto.CustomerDTO;
import com.aris.gymmanager.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICustomerRepository extends JpaRepository<Customer, Integer>{

//    List<Customer> findCustomerByLastName(String lastName);
//
//    @Query(value = "SELECT c.customer_id, c.first_name, c.last_name, p.title FROM customer c " +
//                        "INNER JOIN plan p " +
//                        "ON p.plan_id=c.plan_id", nativeQuery = true)
//    List<Object> findCustomersWithPlan();


    // TODO: Display customers per plan
    List<Customer> findByPlanId(int id);




}
