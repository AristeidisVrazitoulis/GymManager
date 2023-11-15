package com.aris.gymmanager.service;

import com.aris.gymmanager.model.Customer;
import com.aris.gymmanager.model.Plan;
import com.aris.gymmanager.repository.IPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService implements IPlanService {

    private IPlanRepository planRepository;

    @Autowired
    public PlanService(IPlanRepository planRepository){
        this.planRepository = planRepository;
    }

    @Override

    public void save(Plan plan) {
        planRepository.save(plan);
    }

    @Override
    public Plan findPlanById(int id) {
        Optional<Plan> result = planRepository.findById(id);
        Plan thePlan = null;
        if(result.isPresent()){
            thePlan = result.get();
        } else {
          throw new RuntimeException("Plan not found - id :"+id);
        }
        return thePlan;
    }

    @Override
    public List<Plan> findAll(){
        return planRepository.findAll();
    }

    @Override
    public void deletePlanById(int id) {
        planRepository.deleteById(id);
    }
}
