package com.aris.gymmanager.service;

import com.aris.gymmanager.model.Plan;
import com.aris.gymmanager.model.Subscription;
import com.aris.gymmanager.repository.ICustomerRepository;
import com.aris.gymmanager.repository.IPlanRepository;
import com.aris.gymmanager.repository.ISubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService implements ISubscriptionService{

    private IPlanRepository planRepository;
    private ISubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(IPlanRepository planRepository, ISubscriptionRepository subscriptionRepository){
        this.planRepository = planRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Subscription createSubscription(int customerId, String title){
        List<Plan> plans = planRepository.findByTitle(title);
        if(plans == null){
            throw new RuntimeException("Plan '"+title+"' not found");
        }
        Plan thePlan = plans.get(0);

        int planId = thePlan.getId();
        // startDate is always today
        Date startDate = new Date();
        Date endDate = getEndDate(startDate, thePlan.getDuration());

        return new Subscription(customerId, planId, startDate, endDate);
    }

    // returns the date after adding days to the starting date of the plan
    private Date getEndDate(Date startDate, int numDays){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, numDays);
        return calendar.getTime();
    }

    @Override
    public void saveSubscription(Subscription subscription){
        subscriptionRepository.save(subscription);
    }
}
