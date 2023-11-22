package com.aris.gymmanager.restcontroller;


import com.aris.gymmanager.dto.CustomerDTO;
import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Plan;
import com.aris.gymmanager.entity.Subscription;
import com.aris.gymmanager.service.ICustomerService;
import com.aris.gymmanager.service.IPlanService;
import com.aris.gymmanager.service.ISubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private ICustomerService customerService;
    private IPlanService planService;

    @Autowired
    public CustomerController(ICustomerService customerService, IPlanService planService){
        this.customerService = customerService;
        this.planService = planService;
    }


    @GetMapping("/customers")
    public List<Customer> findAll(){

        return customerService.findAll();
    }

    @GetMapping("/customers-plan")
    public List<CustomerDTO> findAllWithPlan(){
        customerService.updateCustomersActivationState();
        List<Customer> customers = customerService.findAll();
        List<CustomerDTO> custs = customerService.convertToDTO(customers);
        return custs;
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
        // Update or create if customer id already exists
        if (customer.getPlan() == null){
            Plan plan = null;
            plan = planService.getPlanByName("NoPlan");
            if(plan == null) {
                throw new RuntimeException("No plan not found");
            }
            customer.setPlan(plan);
        }
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


}
