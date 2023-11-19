package com.aris.gymmanager.restcontroller;


import com.aris.gymmanager.model.Customer;
import com.aris.gymmanager.model.Subscription;
import com.aris.gymmanager.service.ICustomerService;
import com.aris.gymmanager.service.ISubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/customers/search")
    public List<Customer> findCustomerByLastName(@RequestParam String lastName){
        List<Customer> customer = customerService.findCustomerByLastName(lastName);
        if(customer == null){
            throw new RuntimeException("Customer last name:"+lastName+" not Found");
        }
        return customer;
    }

    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody Customer customer){
        // Just in case someone passes an id set it to zero
        // customer.setId(0);
        customerService.save(customer);
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

    // customer that unsubscribes
    @DeleteMapping("/customers/subscribes/{subscriptionId}")
    public Subscription unsubscribeCustomer(@PathVariable int subscriptionId){

        Subscription theSubscription = subscriptionService.findSubscriptionById(subscriptionId);
        if(theSubscription == null){
            throw new RuntimeException("Subscription id:"+subscriptionId+" not Found");
        }
        subscriptionService.deleteSubscriptionById(subscriptionId);
        return theSubscription;
    }

}
