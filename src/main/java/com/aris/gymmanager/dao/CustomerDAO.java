package com.aris.gymmanager.dao;


import com.aris.gymmanager.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

}
