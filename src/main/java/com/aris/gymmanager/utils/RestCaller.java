package com.aris.gymmanager.utils;


import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Plan;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;

@Component
public class RestCaller {

    public RestCaller(){

    }

    public Response getCustomerByIdCall(int id) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");

        Request request = new Request.Builder()
                .url("http://localhost:8080/api/customers/"+id)
                .build();
        return client.newCall(request).execute();

    }


    // Calls REST API to get all customers in JSON form
    public Response getAllCustomersCall(){
        Response response = null;
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            Request request = new Request.Builder()
                    .url("http://localhost:8080/api/customers-plan")
                    .build();

            response = client.newCall(request).execute();

        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    public void deleteCustomerCall(int customerId) throws IOException{
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

    public void createCustomerCall(Customer customer) throws IOException {
        String content;
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");

        // Update or Create
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

    public void saveSubscriptionCall(int id, String planName) throws IOException{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "{\r\n    \"planName\" : \""+planName+"\",\r\n    \"customerId\" : "+id+"\r\n}");
        Request request = new Request.Builder()
                .url("http://localhost:8080/subscribes/")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
    }

    public Response getSubscriptionByCustomerCall(int customerId) throws IOException{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/plans-customer/"+customerId)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }



    // Calls REST API to get all plans in JSON form
    public Response getAllPlansCall(){
        Response response = null;
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            Request request = new Request.Builder()
                    .url("http://localhost:8080/api/plans")
                    .build();

            response = client.newCall(request).execute();

        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }



    public void deletePlanCall(int planId) throws IOException{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/plans/"+planId)
                .method("DELETE", body)
                .build();
        Response response = client.newCall(request).execute();
    }

    public Response getPlanByIdCall(int id) throws IOException{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");

        Request request = new Request.Builder()
                .url("http://localhost:8080/api/plans/"+id)
                .build();
        return client.newCall(request).execute();

    }

    public void createPlanCall(Plan plan) throws IOException {
        String content = String.format(Locale.US, "{\r\n    \"title\" : \"%s\",\r\n    \"duration\" : %d,\r\n    \"description\" : \"%s\",\r\n    \"price\" : %.2f\r\n}",
                plan.getTitle(),
                plan.getDuration(),
                plan.getDescription(),
                plan.getPrice());
        System.out.println(content);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/plans")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
    }


    public Response addPlanToCustomer(String planName, int customerId, String startDate) throws IOException{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"planName\" : \""+planName+"\",\r\n    \"customerId\" : "+customerId+",\r\n    \"startDate\" : \""+startDate+"\"\r\n}");
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/subscribes")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

}
