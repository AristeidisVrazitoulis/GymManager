package com.aris.gymmanager.controller;



import com.aris.gymmanager.model.Customer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.lang.reflect.Type;

@Controller
public class CustomerViewController {



    public CustomerViewController(){

    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/customer-info/{customerId}")
    public String getCustomerInfo(@PathVariable int customerId, Model theModel) throws IOException{
        Response response = getCustomerByIdCall(customerId);
        Gson gson = new Gson();
        Customer customer = gson.fromJson(response.body().string(), Customer.class);
        theModel.addAttribute("customer", customer);
        return "customer/info";
    }

    private Response getCustomerByIdCall(int id) throws IOException{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");

        Request request = new Request.Builder()
                .url("http://localhost:8080/api/customers/"+id)
                .build();
        return client.newCall(request).execute();

    }

    @GetMapping("/customer-list")
    public String getCustomers(Model model) throws IOException {

        Response response = getAllCustomersCall();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Customer>>() {}.getType();

        List<Customer> customers = gson.fromJson(response.body().string(), listType);
        model.addAttribute("customers", customers);
        model.addAttribute("total", customers.size());

        return "customer/all";
    }

    // Calls REST API to get all customers in JSON form
    private Response getAllCustomersCall(){
        Response response = null;
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            Request request = new Request.Builder()
                    .url("http://localhost:8080/api/customers")
                    .build();

            response = client.newCall(request).execute();

        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @GetMapping("/customer-form")
    public String createCustomerForm(Model theModel){
        Customer customer = new Customer();
        theModel.addAttribute("customer", customer);
        return "customer/create-form";
    }

    @PostMapping("/customer-save")
    public String saveCustomer(@ModelAttribute("customer") Customer customer, @RequestParam String planName) throws IOException{

        createCustomerCall(customer);
        return "redirect:/customer-list";

    }

    private void createCustomerCall(Customer customer) throws IOException {
        String content;
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");

        if(customer.getId() != 0) {
            content = "{\r\n  \"id\" : \""+customer.getId()+"\",\r\n  \"firstName\" : \"" + customer.getFirstName() + "\",\r\n    \"lastName\" : \"" + customer.getLastName() + "\"\r\n}";
        } else {
            content = "{\r\n    \"firstName\" : \"" + customer.getFirstName() + "\",\r\n    \"lastName\" : \"" + customer.getLastName() + "\"\r\n}";
        }
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/customers")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
    }

    @GetMapping("/customer-edit")
    public String editCustomer(@RequestParam("customerId") int customerId, Model theModel) throws IOException{
        Response response = getCustomerByIdCall(customerId);

        Gson gson = new Gson();

        Customer customer = gson.fromJson(response.body().string(), Customer.class);
        theModel.addAttribute(customer);

        return "customer/edit";
    }


    @GetMapping("/customer-delete")
    public String deleteCustomer(@RequestParam("customerId") int customerId) throws IOException{
        deleteCustomerCall(customerId);
        // theModel.addAttribute("Message", "Deleted Successfully");
        return "redirect:/customer-list";
    }

    private void deleteCustomerCall(int customerId) throws IOException{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/customers/"+customerId)
                .method("DELETE", body)
                .build();
        Response response = client.newCall(request).execute();
    }

    @PostMapping("/subscribe/{customerId}")
    public String subscribeCustomer(@PathVariable int customerId, @RequestParam String planName, Model theModel) throws IOException{

        makeSubscriptionCall(customerId, planName);
        theModel.addAttribute("message", "subscription updated successfully");

        return "redirect:/customer/info";
    }

    private void makeSubscriptionCall(int id, String planName) throws IOException{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://localhost:8080/customers/subscribes/"+id+"?planName="+planName)
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
    }

}
