package com.aris.gymmanager.restcontroller;


import com.aris.gymmanager.dto.CustomerDTO;
import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.exception.NotFoundException;
import com.aris.gymmanager.exception.InvalidModelException;
import com.aris.gymmanager.service.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private ICustomerService customerService;

    public CustomerController(){

    }
    @Autowired
    public CustomerController(ICustomerService customerService){
        this.customerService = customerService;
    }


    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> findAll(){
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/customers-plan")
    public List<CustomerDTO> findAllWithPlan(){
        // firstly we check if a subscription has expired
        customerService.updateCustomersActivationState();

        List<Customer> customers = customerService.findAll();
        return customerService.convertToDTO(customers);
    }

    @GetMapping("/customers/{customerId}")
    public Customer findCustomerById(@PathVariable int customerId){

        Customer customer = customerService.findCustomerById(customerId);
        if(customer == null){
            throw new NotFoundException("Customer id:"+customerId+" not Found");
        }
        return customer;
    }

    // Fetches all the customers (subscribed or expired) of the plan with id : planId
    @GetMapping("/customers/plan")
    public List<CustomerDTO> getCustomersByPlanTitle(@RequestParam int planId){
        List<Customer> customers = customerService.getCustomersByPlanId(planId);
        return customerService.convertToDTO(customers);
    }


    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@Valid @RequestBody Customer customer, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            throw new InvalidModelException("Invalid Input");
        }

        Customer retCust =  customerService.save(customer);
        return retCust;
    }

    @PutMapping("/customers")
    public Customer updateCustomer(@Valid @RequestBody Customer customer, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new InvalidModelException("Invalid Input");
        }
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping("/customers/{customerId}")
    public void deleteCustomerById(@PathVariable int customerId){
        Customer customer = customerService.findCustomerById(customerId);
        if(customer == null){
            throw new NotFoundException("Customer id:"+customerId+" not Found");
        }
        customerService.deleteCustomerById(customerId);
    }


}
