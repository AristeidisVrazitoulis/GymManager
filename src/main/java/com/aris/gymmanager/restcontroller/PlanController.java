package com.aris.gymmanager.restcontroller;


import com.aris.gymmanager.entity.Plan;
import com.aris.gymmanager.exception.NotFoundException;
import com.aris.gymmanager.service.IPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

        Plan plan = planService.findPlanById(planId);
        if(plan == null){
            throw new NotFoundException("Plan id:"+planId+" not Found");
        }
        return plan;
    }



    @PostMapping("/plans")
    @ResponseStatus(HttpStatus.CREATED)
    public Plan createPlan(@RequestBody Plan plan){
        Plan retPlan = planService.save(plan);
        return retPlan;
    }

    @PutMapping("/plans")
    public Plan updatePlan(@RequestBody Plan plan){
        Plan retPlan = planService.updatePlan(plan);
        return retPlan;
    }


    @DeleteMapping("/plans/{planId}")
    public void deletePlanById(@PathVariable int planId){
        Plan plan = planService.findPlanById(planId);
        if(plan == null){
            throw new NotFoundException("Plan id:"+planId+" not Found");
        }
        planService.deletePlanById(planId);
    }

}
