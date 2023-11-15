package com.aris.gymmanager.repository;

import com.aris.gymmanager.model.Customer;
import com.aris.gymmanager.model.Plan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PlanRepositoryCustom implements  IPlanRepositoryCustom{

    private EntityManager entityManager;
    @Autowired
    public PlanRepositoryCustom(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    @Override
    public List<Plan> findByTitle(String title) {
        TypedQuery<Plan> theQuery = entityManager.createQuery("FROM Plan WHERE title=:theData", Plan.class);
        theQuery.setParameter("theData", title);
        return theQuery.getResultList();
    }
}
