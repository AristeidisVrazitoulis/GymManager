package com.aris.gymmanager.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.Date;

@Entity
@Table(name="subscribes")
@Builder
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


    public Subscription(){

    }

    public Subscription(int customerId, int planId, Date startDate, Date endDate) {
        this.customerId = customerId;
        this.planId = planId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Subscription(int id, int customerId, int planId, Date startDate, Date endDate) {
        this.id = id;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subscription that = (Subscription) o;

        if (id != that.id) return false;
        if (customerId != that.customerId) return false;
        if (planId != that.planId) return false;
        if (!startDate.equals(that.startDate)) return false;
        return endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + customerId;
        result = 31 * result + planId;
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }
}
