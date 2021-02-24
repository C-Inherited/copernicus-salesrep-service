package com.ironhack.salesrep.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.salesrep.dto.CompleteSalesRepDTO;
import com.ironhack.salesrep.dto.SalesRepDTO;
import com.ironhack.salesrep.dto.SalesRepNameDTO;
import com.ironhack.salesrep.service.impl.SalesRepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SalesRepControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private SalesRepService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void getSalesRepById() throws Exception {
        String expectedResponse = "{\"salesRepId\":1,\"name\":\"nerea\",\"opportunities\":null,\"leads\":null}";
        when(service.findById(1)).thenReturn(new CompleteSalesRepDTO(1, "nerea"));

        MvcResult result = mockMvc
                .perform(get("/salesrep/1"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(expectedResponse, result.getResponse().getContentAsString());
    }

    @Test
    void saveSalesRep() throws Exception {
        String expectedResponse = "{\"salesRepId\":1,\"name\":\"nerea\"}";
        when(service.saveSalesRep(new SalesRepNameDTO("nerea"))).thenReturn(new SalesRepDTO(1, "nerea"));

        String body = objectMapper.writeValueAsString(new SalesRepDTO("nerea"));
        MvcResult result = mockMvc
                .perform(post("/salesrep")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals(expectedResponse, result.getResponse().getContentAsString());
    }

    @Test
    void updateSalesRep() throws Exception {
        String expectedResponse = "{\"salesRepId\":1,\"name\":\"laura\"}";
        when(service.updateSalesRep(new SalesRepNameDTO("laura"),1)).thenReturn(new SalesRepDTO(1, "laura"));

        String body = objectMapper.writeValueAsString(new SalesRepDTO("laura"));
        MvcResult result = mockMvc
                .perform(put("/salesrep/1")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals(expectedResponse, result.getResponse().getContentAsString());
    }

    @Test
    void deleteSalesRep() throws Exception {
        MvcResult result = mockMvc
                .perform(delete("/salesrep/1"))
                .andExpect(status().isOk())
                .andReturn();

       verify(service).deleteSalesRep(1);
    }
}
