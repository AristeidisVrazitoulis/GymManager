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

    public RestCaller(){

    }

    public Response getCustomerByIdCall(int id) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

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

    public void saveCustomerCall(Customer customer, String method) throws IOException {
        String content;
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(customer);

        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/customers")
                .method(method, body)
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
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String content = ow.writeValueAsString(plan);

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

//    public void updateCustomerCall(Customer customer) throws Exception{
//        String content;
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/json");
//
//        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//        String json = ow.writeValueAsString(customer);
//
//        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, json);
//        Request request = new Request.Builder()
//                .url("http://localhost:8080/api/customers")
//                .method("PUT", body)
//                .addHeader("Content-Type", "application/json")
//                .build();
//        Response response = client.newCall(request).execute();
//
//    }
}
