package com.ironhack.salesrep.client;

import com.ironhack.salesrep.dto.OpportunityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("opportunities-service")
public interface OpportunityClient {
    @GetMapping("/opportunity/all/{salesRepId}")
    public List<OpportunityDTO> findAllBySalesRepId(@PathVariable int salesRepId);
}
