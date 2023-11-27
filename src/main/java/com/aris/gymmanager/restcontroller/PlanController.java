package com.aris.gymmanager.restcontroller;


import com.aris.gymmanager.dto.SubscriptionDTO;
import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Plan;
import com.aris.gymmanager.exception.CustomerNotFoundException;
import com.aris.gymmanager.exception.PlanNotFoundException;
import com.aris.gymmanager.service.IPlanService;
import com.aris.gymmanager.service.ISubscriptionService;
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
    public Plan findPlanById(@PathVariable int planId){

        Plan customer = planService.findPlanById(planId);
        if(customer == null){
            throw new CustomerNotFoundException("Customer id:"+planId+" not Found");
        }
        return customer;
    }


    @PostMapping("/plans")
    public Plan createPlan(@RequestBody Plan plan){
        planService.save(plan);
        return plan;
    }

    // TODO: edit plan


    @DeleteMapping("/plans/{planId}")
    public String deletePlanById(@PathVariable int planId){
        Plan plan = planService.findPlanById(planId);
        if(plan == null){
            throw new PlanNotFoundException("Plan id:"+planId+" not Found");
        }

        planService.deletePlanById(planId);
        return "Plan with id:"+planId+" removed";
    }

}
