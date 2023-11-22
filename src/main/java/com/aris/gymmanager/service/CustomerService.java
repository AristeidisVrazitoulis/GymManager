package com.aris.gymmanager.service;

import com.aris.gymmanager.dto.CustomerDTO;
import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Subscription;
import com.aris.gymmanager.repository.ICustomerRepository;
import com.aris.gymmanager.repository.ISubscriptionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService implements ICustomerService {

    private ICustomerRepository customerRepository;
    private ISubscriptionRepository subscriptionRepository;

    // TODO : Delete that?
    // private ModelMapper modelMapper;


    @Autowired
    public CustomerService(ICustomerRepository customerRepository, ISubscriptionRepository subscriptionRepository){
        this.customerRepository = customerRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public List<CustomerDTO> convertToDTO(List<Customer> customers){
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for(Customer customer : customers){
            customerDTOS.add(new CustomerDTO(
                    customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getPlan().getTitle(),
                    customer.isActive())
            );
        }
        return customerDTOS;
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

        List<Customer> customers = customerRepository.findAll();
        return customers;
    }



    @Override
    public List<Customer> findCustomerByLastName(String lastName) {
        return customerRepository.findCustomerByLastName(lastName);
    }

    @Override
    public void save(Customer theCustomer) {
        customerRepository.save(theCustomer);
    }


    @Override
    public void deleteCustomerById(int id) {
        customerRepository.deleteById(id);
    }


    @Override
    public void updateCustomersActivationState(){
        List<Subscription> subs = subscriptionRepository.findAll();
        Date now = new Date(); Date startDate; Date endDate;
        Customer customer;

        for(Subscription sub : subs){
            startDate = sub.getStartDate();
            endDate = sub.getEndDate();
            customer = findCustomerById(sub.getCustomerId());
            // if today is greater than startDate AND today is less than endDate then customer is active
            if(now.compareTo(startDate) >= 0 && now.compareTo(endDate) <= 0){
                customer.setActive(true);
                // update database
                save(customer);

            }else{
                customer.setActive(false);
                // update database
                save(customer);
            }
        }
    }




}
