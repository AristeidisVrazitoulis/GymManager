package com.aris.gymmanager.mvccontroller;



import com.aris.gymmanager.dto.CustomerDTO;
import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.exception.CustomerNotFoundException;
import com.aris.gymmanager.utils.RestCaller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;

@Controller
public class CustomerViewController {


    private RestCaller restCaller;
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
        Response response = restCaller.getCustomerByIdCall(customerId);
        Gson gson = new Gson();
        Customer customer = gson.fromJson(response.body().string(), Customer.class);
        if (customer == null){
            throw new CustomerNotFoundException("Customer id:"+customerId+" not Found");
        }
        theModel.addAttribute("customer", customer);
        return "customer/info";
    }


    @GetMapping("/customer-list")
    public String getCustomers(Model model) throws IOException {

        Response response = restCaller.getAllCustomersCall();
        Gson gson = new Gson();
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

    @PostMapping("/customer-save")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) throws IOException{

        restCaller.createCustomerCall(customer);
        return "redirect:/customer-list";

    }

    @GetMapping("/customer-edit")
    public String editCustomer(@RequestParam("customerId") int customerId, Model theModel) throws IOException{
        Response response = restCaller.getCustomerByIdCall(customerId);

        Gson gson = new Gson();
        Customer customer = gson.fromJson(response.body().string(), Customer.class);
        theModel.addAttribute(customer);

        return "customer/edit";
    }


    @GetMapping("/customer-delete")
    public String deleteCustomer(@RequestParam("customerId") int customerId) throws IOException{
        restCaller.deleteCustomerCall(customerId);
        // theModel.addAttribute("Message", "Deleted Successfully");
        return "redirect:/customer-list";
    }


    @PostMapping("/subscribe/{customerId}")
    public String subscribeCustomer(@PathVariable int customerId, @RequestParam String planName, Model theModel) throws IOException{

        restCaller.saveSubscriptionCall(customerId, planName);
        theModel.addAttribute("message", "subscription updated successfully");

        return "redirect:/customer/info";
    }





}
