package com.aris.gymmanager.service;

import com.aris.gymmanager.dto.CustomerDTO;
import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Subscription;
import com.aris.gymmanager.exception.CustomerNotFoundException;
import com.aris.gymmanager.repository.ICustomerRepository;
import com.aris.gymmanager.repository.ISubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Date;

@Service
public class CustomerService implements ICustomerService {

    private ICustomerRepository customerRepository;
    private ISubscriptionRepository subscriptionRepository;



    @Autowired
    public CustomerService(ICustomerRepository customerRepository, ISubscriptionRepository subscriptionRepository){
        this.customerRepository = customerRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public List<CustomerDTO> convertToDTO(List<Customer> customers){
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        String title;
        for(Customer customer : customers){

            title = (customer.getPlan() == null ) ? "NoPlan" : customer.getPlan().getTitle();

            customerDTOS.add(new CustomerDTO(
                    customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    title,
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
        }
        return theCustomer;
    }

    @Override
    public List<Customer> findAll(){

        List<Customer> customers = customerRepository.findAll();
        return customers;
    }

// TODO: Delete that?
//    @Override
//    public List<Customer> findCustomerByLastName(String lastName) {
//        return customerRepository.findCustomerByLastName(lastName);
//    }

    public Customer updateCustomer(Customer customer){

        if(!customerRepository.existsById(customer.getId())){
            throw new CustomerNotFoundException("Customer not found");
        }
        Customer retrievedCustomer = findCustomerById(customer.getId());
        if(retrievedCustomer == null){
            throw new CustomerNotFoundException("Customer not found");
        }

        retrievedCustomer = customer;

        return save(retrievedCustomer);
    }

    @Override
    public Customer save(Customer theCustomer) {
        return customerRepository.save(theCustomer);
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
            if(now.compareTo(startDate) >= 0 && now.compareTo(endDate) <= 0 && !customer.getPlan().getTitle().equals("NoPlan")){
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
