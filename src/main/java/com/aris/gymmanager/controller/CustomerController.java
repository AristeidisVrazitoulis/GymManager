package com.aris.gymmanager.controller;


import com.aris.gymmanager.model.Customer;
import com.aris.gymmanager.model.Subscription;
import com.aris.gymmanager.service.ICustomerService;
import com.aris.gymmanager.service.ISubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private ICustomerService customerService;
    private ISubscriptionService subscriptionService;

    @Autowired
    public CustomerController(ICustomerService customerService, ISubscriptionService subscriptionService){
        this.customerService = customerService;
        this.subscriptionService = subscriptionService;
    }


    @GetMapping("/customers")
    public List<Customer> findAll(){
        return customerService.findAll();
    }

    @GetMapping("/customers/{customerId}")
    public Customer findCustomerById(@PathVariable int customerId){

        Customer customer = customerService.findCustomerById(customerId);
        if(customer == null){
            throw new RuntimeException("Customer id:"+customerId+" not Found");
        }
        return customer;
    }

    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody Customer customer){
        // Just in case someone passes an id set it to zero
        customer.setId(0);

        customerService.save(customer);

        return customer;

    }


    @PutMapping("/customers")
    public Customer updateCustomer(@RequestBody Customer customer){
        customerService.update(customer);
        return customer;
    }

    @DeleteMapping("/customers/{customerId}")
    public String deleteCustomerById(@PathVariable int customerId){
        Customer customer = customerService.findCustomerById(customerId);
        if(customer == null){
            throw new RuntimeException("Customer id:"+customerId+" not Found");
        }
        customerService.deleteCustomerById(customerId);
        return "Customer with id:"+customerId+" removed";
    }

    @PostMapping("/customers/subscribes/{customerId}")
    public Subscription subscribeCustomer(@PathVariable int customerId, @RequestParam String planName){
        Customer customer = customerService.findCustomerById(customerId);
        if(customer == null){
            throw new RuntimeException("Customer id:"+customerId+" not Found");
        }
        Subscription theSubscription = subscriptionService.createSubscription(customerId, planName);
        subscriptionService.saveSubscription(theSubscription);
        return theSubscription;
    }

}
