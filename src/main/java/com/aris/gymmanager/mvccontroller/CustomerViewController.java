package com.aris.gymmanager.mvccontroller;

import com.aris.gymmanager.dto.CustomerDTO;
import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.exception.NotFoundException;
import com.aris.gymmanager.utils.RestCaller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.lang.reflect.Type;

@Controller
public class CustomerViewController {


    private RestCaller restCaller;
    private  Gson gson = new Gson();

    @Autowired
    public CustomerViewController(RestCaller restCaller){
        this.restCaller = restCaller;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/customer-info/{customerId}")
    public String getCustomerInfo(@PathVariable int customerId, Model theModel) throws IOException{
        Response response = restCaller.makeGetRequest("http://localhost:8080/api/customers/"+customerId);
        Customer customer = gson.fromJson(response.body().string(), Customer.class);
        if (customer == null){
            throw new NotFoundException("Customer id:"+customerId+" not Found");
        }
        theModel.addAttribute("customer", customer);
        return "customer/info";
    }


    @GetMapping("/customer-list")
    public String getCustomers(Model model) throws IOException {

        Response response = restCaller.makeGetRequest("http://localhost:8080/api/customers-plan");
        Type listType = new TypeToken<List<CustomerDTO>>() {}.getType();

        // String s = response.body().string();
        List<CustomerDTO> customers = gson.fromJson(response.body().string(), listType);
        model.addAttribute("customers", customers);
        model.addAttribute("total", customers.size());

        return "customer/all";
    }


    @GetMapping("/customer-form")
    public String createCustomerForm(Model theModel){
        Customer customer = new Customer();
        theModel.addAttribute("customer", customer);
        return "customer/create-form";
    }

    @GetMapping("/customer-edit")
    public String editCustomer(@RequestParam("customerId") int customerId, Model theModel) throws IOException{
        Response response = restCaller.makeGetRequest("http://localhost:8080/api/customers/"+customerId);
        Customer customer = gson.fromJson(response.body().string(), Customer.class);
        theModel.addAttribute(customer);

        return "customer/edit";
    }


    @GetMapping("/customer-delete")
    public String deleteCustomer(@RequestParam("customerId") int customerId) throws IOException{
        Response response = restCaller.deleteCustomerCall(customerId);
        return "redirect:/customer-list";
    }

    @PostMapping("/customer-save")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) throws IOException{
        restCaller.saveCustomerCall(customer, "POST");
        return "redirect:/customer-list";
    }

    @PostMapping("/customer-update")
    public String updateCustomer(@ModelAttribute("customer") Customer customer) throws IOException{

        restCaller.saveCustomerCall(customer, "PUT");
        return "redirect:/customer-list";

    }


    @PostMapping("/subscribe/{customerId}")
    public String subscribeCustomer(@PathVariable int customerId, @RequestParam String planName, Model theModel) throws IOException{

        restCaller.saveSubscriptionCall(customerId, planName);
        theModel.addAttribute("message", "subscription updated successfully");
        return "redirect:/customer/info";
    }





}
