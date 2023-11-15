package com.aris.gymmanager.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="subscribes")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="subscription_id")
    private int id;


    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private int customerId;

    @JoinColumn(name = "plan_id", referencedColumnName = "plan_id")
    private int planId;

    @Column(name="start_date")
    private Date startDate;
    @Column(name="end_date")
    private Date endDate;

    public Subscription(int customerId, int planId, Date startDate, Date endDate) {
        this.customerId = customerId;
        this.planId = planId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Subscribes{" +
                "id=" + id +
                ", customer_id=" + customerId +
                ", plan_id=" + planId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
