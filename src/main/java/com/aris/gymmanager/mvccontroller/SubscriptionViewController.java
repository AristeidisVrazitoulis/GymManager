package com.aris.gymmanager.mvccontroller;


import com.aris.gymmanager.entity.Plan;
import com.aris.gymmanager.exception.ErrorResponse;
import com.aris.gymmanager.utils.RestCaller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Controller
public class SubscriptionViewController {

    private RestCaller restCaller;
    private Gson gson = new Gson();

    public SubscriptionViewController(RestCaller restCaller) {
        this.restCaller = restCaller;
    }

    @GetMapping("/subscription-form")
    public String showSubscriptionForm(@RequestParam int customerId, @RequestParam String planName, Model model) throws IOException {
        // Get all Plans
        Response response = restCaller.makeGetRequest("http://localhost:8080/api/plans");

        Type listType = new TypeToken<List<Plan>>() {}.getType();
        List<Plan> plans = gson.fromJson(response.body().string(), listType);
        model.addAttribute("plans", plans);
        model.addAttribute("customerId", customerId);
        response.close();
        return "subscription/form";
    }

    @PostMapping(path = "/subscription-save",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {
                    MediaType.APPLICATION_ATOM_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            })
    public String saveSubscriptionForm(@RequestParam Map<String, String> payload, Model model) throws IOException{
        String planName = payload.get("planTitle");
        String startDate = payload.get("startDate");
        Integer customerId = Integer.parseInt(payload.get("customerId"));

        Response response = restCaller.addPlanToCustomer(planName, customerId, startDate);
        if(response.code() == 400){
            ErrorResponse errorResponse = gson.fromJson(response.body().string(), ErrorResponse.class);
            model.addAttribute("errorObject", errorResponse);
            return "error";
        }
        response.close();
        return "redirect:/plan-subscription?customerId="+customerId+"&planName="+planName;
    }

    @GetMapping("/delete-subscription")
    public String deleteSubscription(@RequestParam int subscriptionId, @RequestParam String planName,
                                     @RequestParam int customerId) throws Exception{
        Response response = restCaller.deleteSubscriptionCall(subscriptionId, customerId);
        response.close();
        return "redirect:/plan-subscription?customerId="+customerId+"&planName="+planName;
    }


}
