package com.aris.gymmanager.repository;

import com.aris.gymmanager.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISubscriptionRepository extends JpaRepository<Subscription, Integer> {

}
