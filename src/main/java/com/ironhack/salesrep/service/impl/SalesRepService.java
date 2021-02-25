package com.ironhack.salesrep.service.impl;

import com.ironhack.salesrep.client.LeadClient;
import com.ironhack.salesrep.client.OpportunityClient;
import com.ironhack.salesrep.controller.impl.AuthController;
import com.ironhack.salesrep.dto.*;
import com.ironhack.salesrep.model.SalesRep;
import com.ironhack.salesrep.repository.SalesRepRepository;
import com.ironhack.salesrep.service.interfaces.ISalesRepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SalesRepService implements ISalesRepService {
    @Autowired
    private LeadClient leadClient;

    @Autowired
    private OpportunityClient opportunityClient;

    @Autowired
    private SalesRepRepository salesRepRepository;

    private CircuitBreakerFactory circuitBreakerFactory = new Resilience4JCircuitBreakerFactory();

    public CompleteSalesRepDTO findById(Integer salesRepId) {
        SalesRep salesRep = getSalesRep(salesRepId);
        CircuitBreaker circuitBreakerLead = circuitBreakerFactory.create("leads-service");
        List<LeadDTO> leadDTOList = circuitBreakerLead.run(() -> leadClient.findAllBySalesRepId(salesRepId, "Bearer " + AuthController.getContacLeadAuthOk()), throwable -> leadFallBack(salesRepId));
        CircuitBreaker circuitBreakerOpportunity = circuitBreakerFactory.create("opportunities-service");
        List<OpportunityDTO> opportunityDTOList = circuitBreakerOpportunity.run(() -> opportunityClient.findAllBySalesRepId(salesRepId, "Bearer " + AuthController.getContactOpportunityAuthOk()),
                throwable -> opportunityFallback(salesRepId));
        return new CompleteSalesRepDTO(salesRep.getSalesRepId(), salesRep.getName(), opportunityDTOList, leadDTOList);
    }


    public SalesRepDTO saveSalesRep(SalesRepNameDTO name) {
        SalesRep salesRep = salesRepRepository.save(new SalesRep(name.getName()));
        return new SalesRepDTO(salesRep.getSalesRepId(), salesRep.getName());
    }

    public SalesRepDTO updateSalesRep(SalesRepNameDTO name, Integer salesRepId) {
        SalesRep salesRep = getSalesRep(salesRepId);
        salesRep.setName(name.getName());
        salesRep = salesRepRepository.save(salesRep);
        return new SalesRepDTO(salesRep.getSalesRepId(), salesRep.getName());
    }

    public void deleteSalesRep(Integer salesRepId) {
        SalesRep salesRep = getSalesRep(salesRepId);
        salesRepRepository.deleteById(salesRepId);
    }

    private SalesRep getSalesRep(Integer salesRepId) {
        return salesRepRepository.findById(salesRepId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sales rep not found"));
    }

    private List<OpportunityDTO> opportunityFallback(Integer salesRepId) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Opportunity service is down :(");
    }

    ;

    private List<LeadDTO> leadFallBack(Integer salesRepId) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lead service is down :(");
    }

    ;
}
