package com.ironhack.salesrep.service.impl;

import com.ironhack.salesrep.client.LeadClient;
import com.ironhack.salesrep.client.OpportunityClient;
import com.ironhack.salesrep.dto.*;
import com.ironhack.salesrep.model.SalesRep;
import com.ironhack.salesrep.repository.SalesRepRepository;
import com.ironhack.salesrep.service.interfaces.ISalesRepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class SalesRepService implements ISalesRepService {
    @Autowired
    private LeadClient leadClient;

    @Autowired
    private OpportunityClient opportunityClient;

    @Autowired
    private SalesRepRepository salesRepRepository;

    public CompleteSalesRepDTO findById(Integer salesRepId) {
        SalesRep salesRep = getSalesRep(salesRepId);
        List<LeadDTO> leadDTOList = leadClient.findAllBySalesRepId(salesRepId);
        List<OpportunityDTO> opportunityDTOList = opportunityClient.findAllBySalesRepId(salesRepId);
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
}
