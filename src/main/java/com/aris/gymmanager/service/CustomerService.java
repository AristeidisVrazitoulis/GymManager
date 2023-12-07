package com.aris.gymmanager.service;

import com.aris.gymmanager.dto.CustomerDTO;
import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Plan;
import com.aris.gymmanager.entity.Subscription;
import com.aris.gymmanager.exception.NotFoundException;
import com.aris.gymmanager.repository.ICustomerRepository;
import com.aris.gymmanager.repository.IPlanRepository;
import com.aris.gymmanager.repository.ISubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

    private ICustomerRepository customerRepository;
    private ISubscriptionRepository subscriptionRepository;
    private IPlanRepository planRepository;



    @Autowired
    public CustomerService(ICustomerRepository customerRepository, ISubscriptionRepository subscriptionRepository, IPlanRepository planRepository) {
        this.customerRepository = customerRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
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

    @Override
    public Customer updateCustomer(Customer customer){

        if(!customerRepository.existsById(customer.getId())){
            throw new NotFoundException("Customer not found");
        }
        Customer retrievedCustomer = findCustomerById(customer.getId());
        if(retrievedCustomer == null){
            throw new NotFoundException("Customer not found");
        }

        retrievedCustomer = customer;

        return save(retrievedCustomer);
    }

    @Override
    public List<Customer> getCustomersByPlanId(int planId){
        if(!planRepository.existsById(planId)){
            throw new NotFoundException("Plan with id:"+planId+" not found");
        }
        return customerRepository.findByPlanId(planId);
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
        List<Customer> customers = customerRepository.findAll();
        Plan plan = null;
        for(Customer cust: customers)
        {
            // Get the subscription id which the customers is currently subscribed
            int subId = customerIsActive(cust);

            if(subId != -1)
            {
                plan = getPlanOfActiveSubscription(subId);
                if(plan == null){
                    throw new NotFoundException("Plan of subscription id:"+subId+" not found");
                }
                cust.setPlan(plan);
                cust.setActive(true);
                save(cust);
            }
            else if(subId == -1 && cust.isActive())
            {
                cust.setActive(false);
                save(cust);
            }
        }
    }

    private Plan getPlanOfActiveSubscription(int subscriptionId){
        Optional<Subscription> result = subscriptionRepository.findById(subscriptionId);
        Subscription subscription = null;

        if(result.isPresent()) subscription = result.get();
        else return null;
        Optional<Plan> planResult = planRepository.findById(subscription.getPlanId());
        Plan plan = null;

        if(planResult.isPresent()) plan = planResult.get();
        else return null;

        return plan;


    }

    // returns the subscription id which is currently active
    private int customerIsActive(Customer cust) {
        Date startDate, endDate;
        Date now = new Date();
        List<Subscription> subs = subscriptionRepository.findSubscriptionsByCustomerId(cust.getId());
        for (Subscription sub : subs) {
            startDate = sub.getStartDate();
            endDate = sub.getEndDate();
            if (now.compareTo(startDate) >= 0 && now.compareTo(endDate) <= 0) {
                return sub.getId();
            }

        }
        return -1;
    }
}
