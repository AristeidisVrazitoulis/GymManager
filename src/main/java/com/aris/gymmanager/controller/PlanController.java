package com.aris.gymmanager.controller;


import com.aris.gymmanager.model.Customer;
import com.aris.gymmanager.model.Plan;
import com.aris.gymmanager.service.IPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlanController {

    private IPlanService planService;

    @Autowired
    public PlanController(IPlanService planService){
        this.planService = planService;
    }


    @GetMapping("/plans")
    public List<Plan> findAll(){
        return planService.findAll();
    }

    @GetMapping("/plans/{planId}")
    public Plan findCustomerById(@PathVariable int planId){

        Plan customer = planService.findPlanById(planId);
        if(customer == null){
            throw new RuntimeException("Customer id:"+planId+" not Found");
        }
        return customer;
    }

    @PostMapping("/plans")
    public Plan createPlan(@RequestBody Plan plan){
        // Just in case someone passes an id set it to zero
        plan.setId(0);

        planService.save(plan);

        return plan;
    }


    @DeleteMapping("/plans/{planId}")
    public String deletePlanById(@PathVariable int planId){
        Plan plan = planService.findPlanById(planId);
        if(plan == null){
            throw new RuntimeException("Plan id:"+planId+" not Found");
        }

        planService.deletePlanById(planId);
        return "Plan with id:"+planId+" removed";
    }

}
