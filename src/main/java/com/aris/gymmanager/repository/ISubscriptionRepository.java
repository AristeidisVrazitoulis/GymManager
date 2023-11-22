package com.aris.gymmanager.repository;

import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISubscriptionRepository extends JpaRepository<Subscription, Integer> {

    List<Subscription> findSubscriptionsByCustomerId(int customerId);
}
