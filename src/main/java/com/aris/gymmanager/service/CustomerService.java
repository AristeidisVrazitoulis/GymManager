package com.aris.gymmanager.service;

import com.aris.gymmanager.model.Customer;
import com.aris.gymmanager.model.Plan;
import com.aris.gymmanager.model.Subscription;
import com.aris.gymmanager.repository.ICustomerRepository;
import com.aris.gymmanager.repository.IPlanRepository;
import com.aris.gymmanager.repository.ISubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

    private ICustomerRepository customerRepository;


    @Autowired
    public CustomerService(ICustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    public void save(Customer theCustomer) {
        customerRepository.save(theCustomer);
    }

    @Override
    public Customer findCustomerById(int id) {
        Optional<Customer> result = customerRepository.findById(id);
        Customer theCustomer = null;
        if(result.isPresent()){
            theCustomer = result.get();
        } else {
          throw new RuntimeException("Customer not found - id :"+id);
        }
        return theCustomer;
    }

    @Override
    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findByLastName(String lastName) {
        return customerRepository.findByLastName(lastName);
    }

    @Override
    public void update(Customer theCustomer) {

        // customerRepository.update(theCustomer);
    }

    @Override
    public void deleteCustomerById(int id) {
        customerRepository.deleteById(id);
    }


}
