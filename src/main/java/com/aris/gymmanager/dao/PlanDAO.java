package com.aris.gymmanager.dao;


import com.aris.gymmanager.model.Plan;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PlanDAO implements IPlanDAO{


    private EntityManager entityManager;

    @Autowired
    public PlanDAO(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Plan thePlan){
        entityManager.persist(thePlan);
    }


}
