package com.aris.gymmanager;

import com.aris.gymmanager.entity.Customer;
import com.aris.gymmanager.repository.ICustomerRepository;
import com.aris.gymmanager.restcontroller.CustomerController;
import com.aris.gymmanager.service.ICustomerService;
import com.aris.gymmanager.service.IPlanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private ICustomerService customerService;
    @Mock
    private IPlanService planService;

    @InjectMocks
    private CustomerController customerController;

    Customer cust1 = new Customer(100,"Aris", "Vrazitoulis", "dsdssd@gmail.com", "0000000000");
    Customer cust2 = new Customer(101, "Nikos", "Vrazitoulis", "dd@gmail.com", "1111111111");
    Customer cust3 = new Customer(102,"Giorgos", "Nakos", "nakos@gmail.com", "2222222222");

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void getAllCustomersException() throws Exception{
        List<Customer> records = new ArrayList<>(Arrays.asList(cust1, cust2, cust3));

        Mockito.when(customerService.findAll()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].firstName").value("Giorgos"));

    }

    @Test
    public void getBookByIdTest() throws Exception {
        Mockito.when(customerService.findCustomerById(cust1.getId())).thenReturn(cust1);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/customers/100")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                        .andExpect(jsonPath("$.firstName").value("Aris"));
    }


    @Test
    public void createCustomerTest() throws Exception {

        Customer cust = Customer.builder()
                .id(103)
                .firstName("Harry")
                .lastName("Potter")
                .active(false)
                .email("harryPotter@gmail.com")
                .phone("1921921922")
                .plan(null)
                .build();

        Mockito.when(customerService.save(cust)).thenReturn(cust);

        String content = objectWriter.writeValueAsString(cust);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName").value("Harry"));

    }


}
