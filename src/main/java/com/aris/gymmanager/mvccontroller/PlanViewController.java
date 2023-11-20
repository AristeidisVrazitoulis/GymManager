package com.aris.gymmanager.mvccontroller;


import com.aris.gymmanager.entity.Plan;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

@Controller
public class PlanViewController {



    @GetMapping("/plan-list")
    public String getPlans(Model model) throws IOException {

        Response response = getAllPlansCall();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Plan>>() {}.getType();

        List<Plan> plans = gson.fromJson(response.body().string(), listType);
        model.addAttribute("plans", plans);
        model.addAttribute("total", plans.size());

        return "plan/all";
    }

    @GetMapping("/plan-subscription")
    public String getPlanSubscriptions(@RequestParam int customerId, @RequestParam String planName, Model model) throws IOException {

        Response response = getAllPlansCall();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Plan>>() {}.getType();

        List<Plan> plans = gson.fromJson(response.body().string(), listType);
        model.addAttribute("plans", plans);

        return "customer/subscriptions";
    }



    // Calls REST API to get all plans in JSON form
    private Response getAllPlansCall(){
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

    @GetMapping("/plan-form")
    public String createPlanForm(Model theModel){
        Plan plan = new Plan();
        theModel.addAttribute("plan", plan);
        return "plan/create-form";
    }

    @PostMapping("/plan-save")
    public String savePlan(@ModelAttribute("plan") Plan plan) throws IOException{
        System.out.println(plan);
        createPlanCall(plan);
        return "redirect:/plan-list";

    }

    private void createPlanCall(Plan plan) throws IOException {
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

    @GetMapping("/plan-edit")
    public String editPlan(@RequestParam("planId") int planId, Model theModel) throws IOException{
        Response response = getPlanByIdCall(planId);

        Gson gson = new Gson();

        Plan plan = gson.fromJson(response.body().string(), Plan.class);
        theModel.addAttribute(plan);

        return "plan/edit";
    }

    private Response getPlanByIdCall(int id) throws IOException{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");

        Request request = new Request.Builder()
                .url("http://localhost:8080/api/plans/"+id)
                .build();
        return client.newCall(request).execute();

    }


    @GetMapping("/plan-delete")
    public String deletePlan(@RequestParam("planId") int planId) throws IOException{
        deletePlanCall(planId);
        // theModel.addAttribute("Message", "Deleted Successfully");
        return "redirect:/plan-list";
    }

    private void deletePlanCall(int planId) throws IOException{
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



}
