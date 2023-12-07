package com.aris.gymmanager;


import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.repository.ICustomerRepository;
import com.aris.gymmanager.restcontroller.CustomerController;
import com.aris.gymmanager.service.ICustomerService;
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


@WebMvcTest(CustomerController.class)
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@AutoConfigureMockMvc(addFilters = false)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @MockBean
    private ICustomerService customerService;
    @MockBean
    private ICustomerRepository customerRepository;


    Customer cust1;
    Customer cust2;
    Customer cust3;

    @BeforeEach
    public void setUp(){
        cust1 = new Customer(100,"Aris", "Vrazitoulis", "dsdssd@gmail.com", "0000000000");
        cust2 = new Customer(101, "Nikos", "Georgiou", "dd@gmail.com", "1111111111");
        cust3 = new Customer(102,"Giorgos", "Nakos", "nakos@gmail.com", "2222222222");

    }

    // GET - /api/customers/
    // Fetch all customers
    @Test
    public void getAllCustomersTest() throws Exception{
        List<Customer> records = new ArrayList<>(Arrays.asList(cust1, cust2, cust3));
        List<Customer> all = customerService.findAll();
        when(all).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].firstName").value("Giorgos"));

    }


    // GET - /api/customers/{customerId}
    // Fetch customer by id
    @Test
    public void getCustomerByIdTest() throws Exception {
        when(customerService.findCustomerById(cust1.getId())).thenReturn(cust1);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/customers/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Aris"));
    }


    // POST - /api/customers
    // Create a customer
    @Test
    public void createCustomerTest() throws Exception {
        Customer cust = Customer.builder()
                .id(40)
                .firstName("H")
                .lastName("P")
                .active(false)
                .email("har@gmail.com")
                .phone("1933321922")
                .plan(null)
                .build();

        String content = objectWriter.writeValueAsString(cust);
        when(customerService.save(any(Customer.class))).thenReturn(cust);


        ResultActions response = mockMvc.perform(post("/api/customers")
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
    public void updateCustomerTest() throws Exception {
        Customer updatedRecord = Customer.builder()
                .id(102)
                .firstName("Harry")
                .lastName("Dursley")
                .active(false)
                .email("hasrryPotter@gmail.com")
                .phone("1941921922")
                .plan(null)
                .build();

        when(customerService.updateCustomer(any(Customer.class))).thenReturn(updatedRecord);

        String updatedContent = objectWriter.writeValueAsString(updatedRecord);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Dursley"));

    }

    // DELETE - /api/customers/{customerId}
    // Delete a customer by its id
    @Test
    public void deleteCustomerTest() throws Exception {
        when(customerService.findCustomerById(cust2.getId())).thenReturn(cust2);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/customers/101")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }





}
