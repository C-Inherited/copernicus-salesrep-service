package com.ironhack.salesrep.service.impl;

import com.ironhack.salesrep.client.LeadClient;
import com.ironhack.salesrep.client.OpportunityClient;
import com.ironhack.salesrep.dto.CompleteSalesRepDTO;
import com.ironhack.salesrep.dto.SalesRepDTO;
import com.ironhack.salesrep.dto.SalesRepNameDTO;
import com.ironhack.salesrep.model.SalesRep;
import com.ironhack.salesrep.repository.SalesRepRepository;
import io.vavr.collection.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class SalesRepServiceTest {

    @Autowired
    private SalesRepService salesRepService;

    @MockBean
    private SalesRepRepository salesRepRepository;
    @MockBean
    private LeadClient leadClient;
    @MockBean
    private OpportunityClient opportunityClient;

    @BeforeEach
    void setUp() {

    }

    @Test
    void findSalesRepById() {
        when(leadClient.findAllBySalesRepId(anyInt(), anyString())).thenReturn(null);
        when(opportunityClient.findAllBySalesRepId(anyInt(),anyString())).thenReturn(null);
        when(salesRepRepository.findById(1)).thenReturn(Optional.of(new SalesRep("sergito")));

        CompleteSalesRepDTO completeSalesRepDTO = salesRepService.findById(1);

        assertEquals(completeSalesRepDTO.getName(), "sergito");
    }

    @Test
    void findSalesRepById_exception_not_found() {
        when(salesRepRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            salesRepService.findById(1);
        });
    }

    @Test
    void findSalesRepById_lead_down() {
        when(salesRepRepository.findById(1)).thenReturn(Optional.empty());
        when(leadClient.findAllBySalesRepId(anyInt(),anyString())).thenThrow(ResponseStatusException.class);

        assertThrows(ResponseStatusException.class, () -> {
            salesRepService.findById(1);
        });
    }

    @Test
    void findSalesRepById_opportunity_down() {
        when(salesRepRepository.findById(1)).thenReturn(Optional.empty());
        when(leadClient.findAllBySalesRepId(anyInt(),anyString())).thenReturn(null);
        when(opportunityClient.findAllBySalesRepId(anyInt(),anyString())).thenThrow(ResponseStatusException.class);

        assertThrows(ResponseStatusException.class, () -> {
            salesRepService.findById(1);
        });
    }

    @Test
    void saveSalesRep() {
        when(salesRepRepository.save(new SalesRep("sergito"))).thenReturn(new SalesRep("sergito"));

        SalesRepDTO salesRep = salesRepService.saveSalesRep(new SalesRepNameDTO("sergito"));

        assertEquals(salesRep.getName(), "sergito");
    }

    @Test
    void updateSalesRep() {
        when(salesRepRepository.findById(1)).thenReturn(Optional.of(new SalesRep("sergito")));
        when(salesRepRepository.save(new SalesRep("pepito"))).thenReturn(new SalesRep("pepito"));

        SalesRepDTO salesRep = salesRepService.updateSalesRep(new SalesRepNameDTO("pepito"),1);

        assertEquals(salesRep.getName(), "pepito");
    }

    @Test
    void deleteSalesRep() {
        when(salesRepRepository.findById(1)).thenReturn(Optional.of(new SalesRep("sergito")));

        salesRepService.deleteSalesRep(1);

        verify(salesRepRepository).deleteById(1);
    }
}
