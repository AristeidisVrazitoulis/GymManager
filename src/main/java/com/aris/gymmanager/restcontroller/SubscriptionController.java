package com.aris.gymmanager.restcontroller;

import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Subscription;
import com.aris.gymmanager.service.ICustomerService;
import com.aris.gymmanager.service.ISubscriptionService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class SubscriptionController {


    private ICustomerService customerService;
    private ISubscriptionService subscriptionService;

    public SubscriptionController(ICustomerService customerService, ISubscriptionService subscriptionService) {
        this.customerService = customerService;
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/subscribes")
    public Subscription subscribeCustomer(@RequestBody HashMap<String, String> payload){
        String planName = payload.get("planName");
        Integer customerId = Integer.parseInt(payload.get("customerId"));
        String startDateText = payload.get("startDate");
        System.out.println(startDateText);
        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateText);
        } catch(ParseException e){
//            e.printStackTrace();
            throw new RuntimeException("Error parsing text date to date Object");
        }

        Customer customer = customerService.findCustomerById(customerId);
        if(customer == null){
            throw new RuntimeException("Customer id:"+customerId+" not Found");
        }
        Subscription theSubscription = subscriptionService.createSubscription(customer, planName, startDate);
        subscriptionService.saveSubscription(theSubscription);
        return theSubscription;
    }

    // customer that unsubscribes
    @DeleteMapping("/subscribes/{subscriptionId}")
    public Subscription unsubscribeCustomer(@PathVariable int subscriptionId){

        Subscription theSubscription = subscriptionService.findSubscriptionById(subscriptionId);
        if(theSubscription == null){
            throw new RuntimeException("Subscription id:"+subscriptionId+" not Found");
        }
        subscriptionService.deleteSubscriptionById(subscriptionId);
        return theSubscription;
    }
}