package com.ironhack.salesrep.client;

import com.ironhack.salesrep.auth.dto.AuthenticationRequest;
import com.ironhack.salesrep.dto.LeadDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("leads-service")
public interface LeadClient {
    @GetMapping("/leads/all/{salesRepId}")
    public List<LeadDTO> findAllBySalesRepId(@PathVariable int salesRepId, @RequestHeader(value = "Authorization") String authorizationHeader);

    @RequestMapping(value = "/leads/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest);
}
