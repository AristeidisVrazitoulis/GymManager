package com.aris.gymmanager.repository;

import com.aris.gymmanager.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomerRepositoryCustom implements ICustomerRepositoryCustom{


    private EntityManager entityManager;
    // inject entity manager using constructor injection
    @Autowired
    public CustomerRepositoryCustom(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Customer> findByLastName(String lastName){
        TypedQuery<Customer> theQuery = entityManager.createQuery("FROM Customer WHERE lastName=:theData", Customer.class);
        theQuery.setParameter("theData", lastName);
        return theQuery.getResultList();
    }


}
