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


    public Response makeGetRequest(String endpoint) throws IOException {

        Request request = new Request.Builder()
                .url(endpoint)
                .build();
        Response response = client.newCall(request).execute();
        return response;

    }



//    public Response getCustomerByIdCall(int id) throws IOException {
//
//        Request request = new Request.Builder()
//                .url("http://localhost:8080/api/customers/"+id)
//                .build();
//        Response response = client.newCall(request).execute();
//        return response;
//
//    }
//
//
//    // Calls REST API to get all customers in JSON form
//    public Response getAllCustomersCall() throws IOException{
//        Request request = new Request.Builder()
//                .url("http://localhost:8080/api/customers-plan")
//                .build();
//
//        Response response = client.newCall(request).execute();
//        return response;
//    }

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
    }

    public void saveSubscriptionCall(int id, String planName) throws IOException{
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
        MediaType mediaType = MediaType.parse("text/plain");
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/plans-customer/"+customerId)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }



    // Calls REST API to get all plans in JSON form
    public Response getAllPlansCall() throws IOException{
        Response response = null;
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/plans")
                .build();

        response = client.newCall(request).execute();
        return response;
    }



    public void deletePlanCall(int planId) throws IOException{
        MediaType mediaType = MediaType.parse("text/plain");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/plans/"+planId)
                .method("DELETE", body)
                .build();
        Response response = client.newCall(request).execute();
    }

    public Response getPlanByIdCall(int id) throws IOException{

        Request request = new Request.Builder()
                .url("http://localhost:8080/api/plans/"+id)
                .build();
        return client.newCall(request).execute();

    }

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
    }

    // POST
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


    // GET
    public Response getCustomersByPlan(int planId) throws IOException{
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/customers/plan?planId="+planId)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }
}
