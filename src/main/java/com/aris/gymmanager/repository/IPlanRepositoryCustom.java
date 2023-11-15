package com.aris.gymmanager.repository;

import com.aris.gymmanager.model.Plan;

import java.util.List;

public interface IPlanRepositoryCustom {

    List<Plan> findByTitle(String title);
}
