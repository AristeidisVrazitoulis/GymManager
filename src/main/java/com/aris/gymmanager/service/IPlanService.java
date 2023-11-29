package com.aris.gymmanager.service;

import com.aris.gymmanager.dto.CustomerDTO;
import com.aris.gymmanager.entity.Plan;

import java.util.List;

public interface IPlanService {

    Plan save(Plan plan);

    Plan findPlanById(int id);

    List<Plan> findAll();

    Plan getPlanByName(String planName);

    Plan getPlanByCustomerId(int customerId);

    Plan updatePlan(Plan plan);

    void deletePlanById(int id);

}
