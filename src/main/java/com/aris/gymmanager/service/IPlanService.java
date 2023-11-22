package com.aris.gymmanager.service;

import com.aris.gymmanager.dto.CustomerDTO;
import com.aris.gymmanager.entity.Plan;

import java.util.List;

public interface IPlanService {

    void save(Plan plan);

    Plan findPlanById(int id);

    List<Plan> findAll();

    Plan getPlanByName(String planName);

    void deletePlanById(int id);

}
