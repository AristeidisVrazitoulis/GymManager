package com.aris.gymmanager.mvccontroller;


import com.aris.gymmanager.dto.SubscriptionDTO;
import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Plan;
import com.aris.gymmanager.entity.Subscription;
import com.aris.gymmanager.utils.RestCaller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

@Controller
public class PlanViewController {

    private RestCaller restCaller;

    @Autowired
    public PlanViewController(RestCaller restCaller){
        this.restCaller = restCaller;
    }

    @GetMapping("/plan-list")
    public String getPlans(Model model) throws IOException {

        Response response = restCaller.getAllPlansCall();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Plan>>() {}.getType();

        List<Plan> plans = gson.fromJson(response.body().string(), listType);
        model.addAttribute("plans", plans);
        model.addAttribute("total", plans.size());

        return "plan/all";
    }

    @GetMapping("/plan-subscription")
    public String getPlanSubscriptions(@RequestParam int customerId, @RequestParam String planName, Model model) throws IOException {

        Gson gson = new Gson();
        // Get all Customer's Subscriptions
        Response subscriptionResponse = restCaller.getSubscriptionByCustomerCall(customerId);
        Type listType = new TypeToken<List<SubscriptionDTO>>() {}.getType();
        List<SubscriptionDTO> subscriptions = gson.fromJson(subscriptionResponse.body().string(), listType);

        // Get Customer data
        Response responseCustomer = restCaller.getCustomerByIdCall(customerId);
        Customer customer = gson.fromJson(responseCustomer.body().string(), Customer.class);

        // Get Customer object by calling the RESTful API
        model.addAttribute("customer", customer);
        // Create a Query from Subscription with the customer's subscriptions
        model.addAttribute("subscriptions", subscriptions);
        model.addAttribute("planName", planName);
        // model.addAttribute("plans", plans);

        return "customer/subscriptions";
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
        Response response = restCaller.getPlanByIdCall(planId);

        Gson gson = new Gson();

        Plan plan = gson.fromJson(response.body().string(), Plan.class);
        theModel.addAttribute(plan);

        return "plan/edit";
    }




    @GetMapping("/plan-delete")
    public String deletePlan(@RequestParam("planId") int planId) throws IOException{
        restCaller.deletePlanCall(planId);
        // theModel.addAttribute("Message", "Deleted Successfully");
        return "redirect:/plan-list";
    }





}
