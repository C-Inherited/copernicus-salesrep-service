package com.ironhack.salesrep.client;

import com.ironhack.salesrep.auth.dto.AuthenticationRequest;
import com.ironhack.salesrep.dto.OpportunityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("opportunities-service")
public interface OpportunityClient {
    @GetMapping("/opportunity/all/{salesRepId}")
    public List<OpportunityDTO> findAllBySalesRepId(@PathVariable int salesRepId, @RequestHeader(value = "Authorization") String authorizationHeader);

    @RequestMapping(value = "/opportunity/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest);
}
