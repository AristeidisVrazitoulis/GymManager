package com.aris.gymmanager.service;

import com.aris.gymmanager.dto.SubscriptionDTO;
import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Plan;
import com.aris.gymmanager.entity.Subscription;
import com.aris.gymmanager.exception.PlanNotFoundException;
import com.aris.gymmanager.repository.IPlanRepository;
import com.aris.gymmanager.repository.ISubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SubscriptionService implements ISubscriptionService{

    private IPlanRepository planRepository;
    private ISubscriptionRepository subscriptionRepository;

    private IPlanService planService;

    @Autowired
    public SubscriptionService(IPlanRepository planRepository,
                               ISubscriptionRepository subscriptionRepository,
                               IPlanService planService)
    {
        this.planRepository = planRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.planService = planService;
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
    public Subscription createSubscription(Customer customer, String title, Date startDate){
        List<Plan> plans = planRepository.findPlanByTitle(title);
        if(plans == null){
            throw new PlanNotFoundException("Plan '"+title+"' not found");
        }

        if(plans.size() > 1 ){
            System.out.println("There are more than one plans with the title: "+title);
        }
        Plan thePlan = plans.get(0);

        //  add the one-to-many relationship
        thePlan.add(customer);

        // endDate = startDate + numOfDays
        Date endDate = getEndDate(startDate, thePlan.getDuration());

        return new Subscription(customer.getId(), thePlan.getId(), startDate, endDate);
    }

    // returns the date after adding days to the starting date of the plan
    private Date getEndDate(Date startDate, int numDays){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, numDays);
        return calendar.getTime();
    }

    @Override
    public Subscription saveSubscription(Subscription subscription){

        return subscriptionRepository.save(subscription);
    }

    @Override
    public void deleteSubscriptionById(int subscriptionId){
        subscriptionRepository.deleteById(subscriptionId);
    }

    @Override
    public List<Subscription> findSubscriptionsByCustomerId(int customerId){
        // Check if customer id exists
        List<Subscription> subscriptions = subscriptionRepository.findSubscriptionsByCustomerId(customerId);
        return subscriptions;
    }


    @Override
    public List<SubscriptionDTO> convertToDTO(List<Subscription> subscriptions){
        List<SubscriptionDTO> subscriptionDTOS = new ArrayList<>();
        Plan thePlan = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        for(Subscription sub : subscriptions){
            thePlan = planService.findPlanById(sub.getPlanId());

            subscriptionDTOS.add(new SubscriptionDTO(
                    sub.getId(),
                    thePlan.getTitle(),
                    dateFormat.format(sub.getStartDate()),
                    dateFormat.format(sub.getEndDate()))
            );
        }
        return subscriptionDTOS;
    }



}
