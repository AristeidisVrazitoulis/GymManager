package com.aris.gymmanager.service;

import com.aris.gymmanager.model.Subscription;

import java.util.Date;

public interface ISubscriptionService {

    Subscription findSubscriptionById(int subscriptionId);
    Subscription createSubscription(int customerId, String planName);
    void saveSubscription(Subscription subscription);
    void deleteSubscriptionById(int subscriptionId);

}
