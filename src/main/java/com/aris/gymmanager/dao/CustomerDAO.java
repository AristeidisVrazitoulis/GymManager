package com.aris.gymmanager.dao;


import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Subscribes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAO implements ICustomerDAO {

    // define field for entity manager
    private EntityManager entityManager;
    // inject entity manager using constructor injection
    @Autowired
    public CustomerDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //implement save method
    @Override
    @Transactional
    public void save(Customer theCustomer){
        entityManager.persist((theCustomer));
    }

    @Override
    public Customer findCustomerById(int id) {
        return entityManager.find(Customer.class, id);
    }

    @Override
    public List<Customer> findAll() {
        TypedQuery<Customer> theQuery = entityManager.createQuery("FROM Customer order by lastName", Customer.class);
        return theQuery.getResultList();
    }

    @Override
    public List<Customer> findByLastName(String lastName) {
        TypedQuery<Customer> theQuery = entityManager.createQuery("FROM Customer WHERE lastName=:theData", Customer.class);
        theQuery.setParameter("theData", lastName);
        return theQuery.getResultList();

    }

    @Override
    @Transactional
    public void update(Customer theCustomer) {
        entityManager.merge(theCustomer);
    }

    @Override
    @Transactional
    public void deleteCustomerById(int id) {
        Customer customer = findCustomerById(id);
        entityManager.remove(customer);
    }

}
