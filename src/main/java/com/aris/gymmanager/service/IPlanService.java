package com.aris.gymmanager.service;

import com.aris.gymmanager.model.Customer;
import com.aris.gymmanager.model.Plan;

import java.util.List;

public interface IPlanService {

    void save(Plan plan);

    Plan findPlanById(int id);

    List<Plan> findAll();

    void deletePlanById(int id);
}
