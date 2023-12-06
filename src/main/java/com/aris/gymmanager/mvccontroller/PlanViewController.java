package com.aris.gymmanager.mvccontroller;


import com.aris.gymmanager.dto.CustomerDTO;
import com.aris.gymmanager.dto.SubscriptionDTO;
import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Plan;
import com.aris.gymmanager.utils.RestCaller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@Controller
public class PlanViewController {

    private RestCaller restCaller;
    private Gson gson = new Gson();
    @Autowired
    public PlanViewController(RestCaller restCaller){
        this.restCaller = restCaller;
    }

    @GetMapping("/plan-list")
    public String getPlans(Model model) throws IOException {

        Response response = restCaller.makeGetRequest("http://localhost:8080/api/plans");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Plan>>() {}.getType();

        List<Plan> plans = gson.fromJson(response.body().string(), listType);
        model.addAttribute("plans", plans);
        model.addAttribute("total", plans.size());
        response.close();
        return "plan/all";
    }

    @GetMapping("/plan-subscription")
    public String getPlanSubscriptions(@RequestParam int customerId, @RequestParam String planName, Model model) throws IOException {

        // Get all Customer's Subscriptions
        Response subscriptionResponse = restCaller.makeGetRequest("http://localhost:8080/api/plans-customer/"+customerId);
        Type listType = new TypeToken<List<SubscriptionDTO>>() {}.getType();
        List<SubscriptionDTO> subscriptions = gson.fromJson(subscriptionResponse.body().string(), listType);

        // Get Customer data
        Response responseCustomer = restCaller.makeGetRequest("http://localhost:8080/api/customers/"+customerId);
        Customer customer = gson.fromJson(responseCustomer.body().string(), Customer.class);

        // Get Customer object by calling the RESTful API
        model.addAttribute("customer", customer);
        // Create a Query from Subscription with the customer's subscriptions
        model.addAttribute("subscriptions", subscriptions);
        model.addAttribute("planName", planName);
        // model.addAttribute("plans", plans);
        subscriptionResponse.close();
        return "customer/subscriptions";
    }


    @GetMapping("/customersByPlan")
    public String showCustomersByPlan(@RequestParam int planId, Model model) throws Exception{
        // Get all Plans' Customers
        Response customersResponse = restCaller.makeGetRequest("http://localhost:8080/api/customers/plan?planId="+planId);
        Type listType = new TypeToken<List<CustomerDTO>>() {}.getType();
        List<CustomerDTO> customers = gson.fromJson(customersResponse.body().string(), listType);

        Response planResponse = restCaller.makeGetRequest("http://localhost:8080/api/plans/"+planId);
        Plan plan = gson.fromJson(planResponse.body().string(), Plan.class);

        model.addAttribute("customers", customers);

        model.addAttribute("planTitle", plan.getTitle());
        customersResponse.close();
        return "plan/customers-by-plan";
    }



    @GetMapping("/plan-form")
    public String createPlanForm(Model theModel){
        Plan plan = new Plan();
        theModel.addAttribute("plan", plan);
        return "plan/create-form";
    }




    @PostMapping("/plan-save")
    public String savePlan(@ModelAttribute("plan") Plan plan) throws IOException{
        restCaller.savePlanCall(plan, "POST");
        return "redirect:/plan-list";

    }

    @PostMapping("/plan-update")
    public String updatePlan(@ModelAttribute("plan") Plan plan) throws IOException{
        restCaller.savePlanCall(plan, "PUT");
        return "redirect:/plan-list";

    }



    @GetMapping("/plan-edit")
    public String editPlan(@RequestParam("planId") int planId, Model theModel) throws IOException{
        Response response = restCaller.makeGetRequest("http://localhost:8080/api/plans/"+planId);

        Plan plan = gson.fromJson(response.body().string(), Plan.class);
        theModel.addAttribute(plan);
        response.close();
        return "plan/edit";
    }




    @GetMapping("/plan-delete")
    public String deletePlan(@RequestParam("planId") int planId) throws IOException{
        restCaller.deletePlanCall(planId);
        return "redirect:/plan-list";
    }





}
