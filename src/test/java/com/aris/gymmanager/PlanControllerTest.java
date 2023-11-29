package com.aris.gymmanager;


import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Plan;
import com.aris.gymmanager.restcontroller.PlanController;

import com.aris.gymmanager.service.IPlanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlanController.class)
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@AutoConfigureMockMvc(addFilters = false)
public class PlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @MockBean
    private IPlanService planService;

    Plan plan1;
    Plan plan2;
    Plan plan3;

    @BeforeEach
    public void setUp(){
        plan1 = new Plan(100,"title1", 30, "d1", 30, null);
        plan2 = new Plan(101, "title2", 360, "d2", 180, null);
        plan3 = new Plan(102,"title3", 180, "d3", 100, null);

    }


    // GET - /api/plans/
    // Fetch all plan
    @Test
    public void getAllPlansTest() throws Exception{
        List<Plan> records = new ArrayList<>(Arrays.asList(plan1, plan2, plan3));
        List<Plan> all = planService.findAll();
        when(all).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/plans")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].title").value("title3"));
    }

    // GET - /api/plans
    // Fetch all plans test
    @Test
    public void getPlanByIdTest() throws Exception{
        when(planService.findPlanById(plan1.getId())).thenReturn(plan1);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/plans/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title1"));
    }



    // POST - /api/plans
    // Create a plan
    @Test
    public void createPlanTest() throws Exception {
        Plan plan = Plan.builder()
                .id(40)
                .title("new_title")
                .duration(30)
                .description("new_desc")
                .price(0.0f)
                .build();

        String content = objectWriter.writeValueAsString(plan);
        when(planService.save(plan)).thenReturn(plan);


        ResultActions response = mockMvc.perform(post("/api/plans")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andDo(MockMvcResultHandlers.print());

    }


    // PUT - /api/customers
    // Create a customer
    @Test
    public void updatePlanTest() throws Exception {
        Plan updatedRecord = Plan.builder()
                .id(100)
                .title("new_title")
                .duration(30)
                .description("new_desc")
                .price(0.0f)
                .build();

        when(planService.updatePlan(updatedRecord)).thenReturn(updatedRecord);

        String updatedContent = objectWriter.writeValueAsString(updatedRecord);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/plans")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("new_title"));

    }


    // DELETE - /api/plans/{planId}
    // Delete a customer by its id
    @Test
    public void deletePlanTest() throws Exception {
        when(planService.findPlanById(plan2.getId())).thenReturn(plan2);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/plans/101")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }




}
