package com.aris.gymmanager.restcontroller;


import com.aris.gymmanager.dto.CustomerDTO;
import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Plan;
import com.aris.gymmanager.exception.CustomerNotFoundException;
import com.aris.gymmanager.exception.InvalidModelException;
import com.aris.gymmanager.exception.PlanNotFoundException;
import com.aris.gymmanager.service.ICustomerService;
import com.aris.gymmanager.service.IPlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
        customerService.updateCustomersActivationState();
        List<Customer> customers = customerService.findAll();
        return customerService.convertToDTO(customers);
    }

    @GetMapping("/customers/{customerId}")
    public Customer findCustomerById(@PathVariable int customerId){

        Customer customer = customerService.findCustomerById(customerId);
        if(customer == null){
            throw new CustomerNotFoundException("Customer id:"+customerId+" not Found");
        }
        return customer;
    }

    // TODO: Delete that?
//    @GetMapping("/customers/search")
//    public List<Customer> findCustomerByLastName(@RequestParam String lastName){
//        List<Customer> customer = customerService.findCustomerByLastName(lastName);
//        if(customer == null){
//            throw new CustomerNotFoundException("Customer last name:"+lastName+" not Found");
//        }
//        return customer;
//    }

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
    public Customer updateCustomer(@Valid @RequestBody Customer customer){

        return customerService.updateCustomer(customer);
    }

    @DeleteMapping("/customers/{customerId}")
    public void deleteCustomerById(@PathVariable int customerId){
        Customer customer = customerService.findCustomerById(customerId);
        if(customer == null){
            throw new CustomerNotFoundException("Customer id:"+customerId+" not Found");
        }
        customerService.deleteCustomerById(customerId);
    }


}
