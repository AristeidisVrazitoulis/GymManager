package com.aris.gymmanager.repository;

import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPlanRepository extends JpaRepository<Plan, Integer> {

    List<Plan> findPlanByTitle(String title);



}
