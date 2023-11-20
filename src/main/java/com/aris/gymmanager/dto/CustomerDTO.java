package com.aris.gymmanager.dto;

import com.aris.gymmanager.entity.Customer;

public class CustomerDTO {

    private int customerId;
    private String firstName;
    private String lastName;
    private String planTitle;


    public CustomerDTO(){

    }

    public CustomerDTO(int customerId, String firstName, String lastName, String planTitle) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.planTitle = planTitle;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", planTitle='" + planTitle + '\'' +
                '}';
    }
}
