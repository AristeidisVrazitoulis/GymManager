package com.aris.gymmanager.service;

import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Plan;
import com.aris.gymmanager.entity.Subscription;
import com.aris.gymmanager.repository.IPlanRepository;
import com.aris.gymmanager.repository.ISubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public Subscription findSubscriptionById(int subscriptionId) {
        Optional<Subscription> result = subscriptionRepository.findById(subscriptionId);
        Subscription theSubscription = null;
        if(result.isPresent()){
            theSubscription = result.get();
        } else {
            throw new RuntimeException("Customer not found - id :"+subscriptionId);
        }
        return theSubscription;
    }

    @Override
    public Subscription createSubscription(Customer customer, String title){
        List<Plan> plans = planRepository.findPlanByTitle(title);
        if(plans == null){
            throw new RuntimeException("Plan '"+title+"' not found");
        }

        if(plans.size() > 1 ){
            System.out.println("There are more than one plans with the title: "+title);
        }
        Plan thePlan = plans.get(0);

        //  add the one-to-many relationship
        thePlan.add(customer);

        int planId = thePlan.getId();
        // startDate is always today
        Date startDate = new Date();
        Date endDate = getEndDate(startDate, thePlan.getDuration());

        return new Subscription(customer.getId(), planId, startDate, endDate);
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

    @Override
    public void deleteSubscriptionById(int subscriptionId){
        subscriptionRepository.deleteById(subscriptionId);
    }
}
