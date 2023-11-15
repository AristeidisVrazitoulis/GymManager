package com.aris.gymmanager.repository;

import com.aris.gymmanager.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlanRepository extends JpaRepository<Plan, Integer>, IPlanRepositoryCustom{


}
