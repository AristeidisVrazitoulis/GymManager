package com.aris.gymmanager.service;

import com.aris.gymmanager.model.Subscription;

import java.util.Date;

public interface ISubscriptionService {

    Subscription createSubscription(int customerId, String planName);
    void saveSubscription(Subscription subscription);
}
