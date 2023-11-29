package com.aris.gymmanager.mvccontroller;


import com.aris.gymmanager.entity.Plan;
import com.aris.gymmanager.utils.RestCaller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.hibernate.annotations.GeneratorType;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SubscriptionViewController {

    private RestCaller restCaller;

    public SubscriptionViewController(RestCaller restCaller) {
        this.restCaller = restCaller;
    }

    @GetMapping("/subscription-form")
    public String showSubscriptionForm(@RequestParam int customerId, @RequestParam String planName, Model model) throws IOException {
        // Get all Plans
        Response response = restCaller.getAllPlansCall();
        Gson gson = new Gson();

        Type listType = new TypeToken<List<Plan>>() {}.getType();
        List<Plan> plans = gson.fromJson(response.body().string(), listType);
        model.addAttribute("plans", plans);
        model.addAttribute("customerId", customerId);
        return "subscription/form";
    }

    // TODO: make sure user puts a date
    @PostMapping(path = "/subscription-save",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {
                    MediaType.APPLICATION_ATOM_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            })
    public String saveSubscriptionForm(@RequestParam Map<String, String> payload) throws IOException{
        String planName = payload.get("planTitle");
        String startDate = payload.get("startDate");
        Integer customerId = Integer.parseInt(payload.get("customerId"));

        Response response = restCaller.addPlanToCustomer(planName, customerId, startDate);

        return "redirect:/plan-subscription?customerId="+customerId+"&planName="+planName;
    }


}
