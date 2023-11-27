package com.aris.gymmanager.service;

import com.aris.gymmanager.dto.SubscriptionDTO;
import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Subscription;

import java.util.Date;
import java.util.List;

public interface ISubscriptionService {

    Subscription findSubscriptionById(int subscriptionId);
    Subscription createSubscription(Customer customer, String planName, Date startDate);
    void saveSubscription(Subscription subscription);
    void deleteSubscriptionById(int subscriptionId);

    List<Subscription> findSubscriptionsByCustomerId(int customerId);

    List<SubscriptionDTO> convertToDTO(List<Subscription> subscriptions);
}
