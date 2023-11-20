package com.aris.gymmanager.service;

import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Subscription;

public interface ISubscriptionService {

    Subscription findSubscriptionById(int subscriptionId);
    Subscription createSubscription(Customer customer, String planName);
    void saveSubscription(Subscription subscription);
    void deleteSubscriptionById(int subscriptionId);

}
