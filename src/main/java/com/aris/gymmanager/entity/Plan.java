package com.aris.gymmanager.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="plan")
@Builder
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private int duration;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private float price;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "plan", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("plan")
    private List<Customer> customers;

    public Plan(){

    }

    public Plan(String title, int duration, String description, float price) {
        this.title = title;
        this.duration = duration;
        this.description = description;
        this.price = price;
    }

    // For testing mostly
    public Plan(int id, String title, int duration, String description, float price, List<Customer> customers) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.description = description;
        this.price = price;
        this.customers = customers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public void add(Customer tempCustomer){
        if(customers == null){
            customers = new ArrayList<>();
        }
        customers.add(tempCustomer);
        tempCustomer.setPlan(this);
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plan plan = (Plan) o;
        return id == plan.id && duration == plan.duration && Float.compare(plan.price, price) == 0 && title.equals(plan.title) && description.equals(plan.description) && Objects.equals(customers, plan.customers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, duration, description, price, customers);
    }
}
