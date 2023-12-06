package com.aris.gymmanager.utils;


import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Plan;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class RestCaller {

    private OkHttpClient client = new OkHttpClient().newBuilder().build();
    private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    public RestCaller(){

    }

    // Makes GET requests
    public Response makeGetRequest(String endpoint) throws IOException {

        Request request = new Request.Builder()
                .url(endpoint)
                .build();
        Response response = client.newCall(request).execute();
        return response;

    }

    // POST - saves a customer to the database
    public void saveCustomerCall(Customer customer, String method) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");


        String json = ow.writeValueAsString(customer);

        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/customers")
                .method(method, body)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        response.close();

    }
    // POST - save a subscription to the database
    public void saveSubscriptionCall(int id, String planName) throws IOException{
        MediaType mediaType = MediaType.parse("application/json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "{\r\n    \"planName\" : \""+planName+"\",\r\n    \"customerId\" : "+id+"\r\n}");
        Request request = new Request.Builder()
                .url("http://localhost:8080/subscribes/")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        response.close();
    }

    // POST - saves a plan to the database
    public void savePlanCall(Plan plan, String method) throws IOException {
        String content = ow.writeValueAsString(plan);

        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/plans")
                .method(method, body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        response.close();
    }

    // POST - customer subscribes to a plan
    public Response addPlanToCustomer(String planName, int customerId, String startDate) throws IOException{
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


    // DELETE - a plan
    public void deletePlanCall(int planId) throws IOException{
        MediaType mediaType = MediaType.parse("text/plain");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/plans/"+planId)
                .method("DELETE", body)
                .build();
        Response response = client.newCall(request).execute();
        response.close();
    }
    // Delete a customer
    public Response deleteCustomerCall(int customerId) throws IOException{
        MediaType mediaType = MediaType.parse("text/plain");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/customers/"+customerId)
                .method("DELETE", body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }


    public Response deleteSubscriptionCall(int subscriptionId, int customerId) throws Exception{
        MediaType mediaType = MediaType.parse("text/plain");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/subscribes/"+subscriptionId+"?customerId="+customerId)
                .method("DELETE", body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }
}
