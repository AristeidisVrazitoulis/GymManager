package com.aris.gymmanager.service;

import com.aris.gymmanager.dto.CustomerDTO;
import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.repository.ICustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService implements ICustomerService {

    private ICustomerRepository customerRepository;

    // TODO : Delete that?
    // private ModelMapper modelMapper;


    @Autowired
    public CustomerService(ICustomerRepository customerRepository, ModelMapper modelMapper){
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> convertToDTO(List<Customer> customers){
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for(Customer customer : customers){
            customerDTOS.add(new CustomerDTO(
                    customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getPlan().getTitle())
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




}
