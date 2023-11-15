package com.aris.gymmanager.repository;

import com.aris.gymmanager.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISubscriptionRepository extends JpaRepository<Subscription, Integer> {

}
