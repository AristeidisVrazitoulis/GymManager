package com.aris.gymmanager;


import com.aris.gymmanager.dto.SubscriptionDTO;
import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.entity.Subscription;
import com.aris.gymmanager.restcontroller.SubscriptionController;
import com.aris.gymmanager.service.ICustomerService;
import com.aris.gymmanager.service.ISubscriptionService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(SubscriptionController.class)
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@AutoConfigureMockMvc(addFilters = false)
public class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @MockBean
    private ISubscriptionService subscriptionService;
    @MockBean
    private ICustomerService customerService;

    Subscription subscription1;
    Subscription subscription2;

    @BeforeEach
    public void setUp() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        String dateInString = "05/06/2024";
        Date date = formatter.parse(dateInString);
        subscription1 = new Subscription(100,0, 0, new Date(), new Date());
        subscription2 = new Subscription(101,1, 0, new Date(), date);

    }


    // GET - /plans-customer/{customerId}
    @Test
    public void getSubscriptionsWithCustomersTest() throws Exception{
        int id = 0;
        String planId0 = "Crossfit";
        List<Subscription> subs = new ArrayList<>(Arrays.asList(subscription1, subscription2));
        List<SubscriptionDTO> subsDTO = new ArrayList<>();
        for(Subscription sub : subs){
            subsDTO.add(new SubscriptionDTO(sub.getId(), planId0, sub.getStartDate().toString(), sub.getEndDate().toString()));
        }
        when(subscriptionService.findSubscriptionsByCustomerId(id)).thenReturn(subs);
        when(subscriptionService.convertToDTO(subs)).thenReturn(subsDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/plans-customer/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].planName").value("Crossfit"));

    }

    // POST - /api/subscribes
    @Test
    public void SubscribeCustomerTest() throws Exception
    {
        int id = 0;
        String planName = "Crossfit";
        String startDateText = "2023-05-06";
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateText);

        Customer cust1 = new Customer(0,"Aris", "Vrazitoulis", "dsdssd@gmail.com", "0000000000");
        String content = "{\"planName\":\""+planName+"\",\"customerId\":\"+"+id+"\",\"startDate\":\""+startDateText+"\"}";
        System.out.println(content);

        when(customerService.findCustomerById(id)).thenReturn(cust1);
        when(subscriptionService.createSubscription(cust1, planName, startDate)).thenReturn(subscription1);
        when(subscriptionService.saveSubscription(subscription1)).thenReturn(subscription1);

        ResultActions response = mockMvc.perform(post("/api/subscribes")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andDo(MockMvcResultHandlers.print());


    }

}
