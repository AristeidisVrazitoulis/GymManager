package com.aris.gymmanager.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="subscribes")
public class Subscribes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="subscription_id")
    private int id;


    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private int customer_id;

    @JoinColumn(name = "plan_id", referencedColumnName = "plan_id")
    private int plan_id;

    @Column(name="start_date")
    private Date startDate;
    @Column(name="end_date")
    private Date endDate;

    public Subscribes(int customer_id, int plan_id, Date startDate, Date endDate) {
        this.customer_id = customer_id;
        this.plan_id = plan_id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
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
                ", customer_id=" + customer_id +
                ", plan_id=" + plan_id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
