package com.aris.gymmanager.dto;
import java.util.Date;
public class SubscriptionDTO {
    private int customerId;
    private String planName;
    private String startDate;
    private String endDate;

    public SubscriptionDTO(int customerId, String planName, String startDate, String endDate) {
        this.customerId = customerId;
        this.planName = planName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "SubscriptionDTO{" +
                "customerId=" + customerId +
                ", planName='" + planName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubscriptionDTO that = (SubscriptionDTO) o;

        if (customerId != that.customerId) return false;
        if (!planName.equals(that.planName)) return false;
        if (!startDate.equals(that.startDate)) return false;
        return endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        int result = customerId;
        result = 31 * result + planName.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }
}
