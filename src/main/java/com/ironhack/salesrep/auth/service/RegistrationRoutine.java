package com.ironhack.salesrep.auth.service;

import com.ironhack.salesrep.auth.dto.AuthenticationRequest;
import com.ironhack.salesrep.client.LeadClient;
import com.ironhack.salesrep.client.OpportunityClient;
import com.ironhack.salesrep.controller.impl.AuthController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Component
public class RegistrationRoutine {

    @Autowired
    LeadClient leadClient;

    @Autowired
    OpportunityClient opportunityClient;

    public static boolean isLeadRegistered = false;
    public static boolean isOpportunityRegistered = false;

    private static final Logger log = LoggerFactory.getLogger(RegistrationRoutine.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final CircuitBreakerFactory circuitBreakerFactory = new Resilience4JCircuitBreakerFactory();

    @Scheduled(fixedRate = 10000)
    public void checkRegistrationLead() {
            if (!isLeadRegistered){
                CircuitBreaker circuitBreaker = circuitBreakerFactory.create("leads-service");
                log.info("Trying to register with validation-service {}", dateFormat.format(new Date()));
                AuthenticationRequest authenticationRequest = new AuthenticationRequest("salesrep-service", "salesrep-service");
                ResponseEntity<?> responseEntity= circuitBreaker.run(() -> opportunityClient.createAuthenticationToken(authenticationRequest), throwable -> fallbackTransaction("validation-service"));
                if (responseEntity != null) {
                    parseJWTLead(responseEntity);
                    isLeadRegistered = true;
                    log.info("Registered with contact-service auth token: {}", AuthController.getContacLeadAuthOk());
                }
            }
    }

    @Scheduled(fixedRate = 10000)
    public void checkRegistrationOpportunity() {
            if (!isOpportunityRegistered){
                CircuitBreaker circuitBreaker = circuitBreakerFactory.create("opportunity-service");
                log.info("Trying to register with account-service {}", dateFormat.format(new Date()));
                AuthenticationRequest authenticationRequest = new AuthenticationRequest("salesrep-service", "salesrep-service");
                ResponseEntity<?> responseEntity= circuitBreaker.run(() -> leadClient.createAuthenticationToken(authenticationRequest), throwable -> fallbackTransaction("account-service"));
                if (responseEntity != null) {
                    parseJWTOpportunity(responseEntity);
                    isOpportunityRegistered = true;
                    log.info("Registered with contact-service auth token: {}", AuthController.getContactOpportunityAuthOk());
                }
            }
    }

    private void parseJWTLead(ResponseEntity<?> responseEntity) {
        String auth = Objects.requireNonNull(responseEntity.getBody()).toString();
        AuthController.setContacLeadAuthOk(auth.substring(5, auth.length() - 1));
    }

    private void parseJWTOpportunity(ResponseEntity<?> responseEntity) {
        String auth = Objects.requireNonNull(responseEntity.getBody()).toString();
        AuthController.setContactOpportunityAuthOk(auth.substring(5, auth.length() - 1));
    }

    private ResponseEntity<?> fallbackTransaction(String serviceName) {
        log.info( serviceName + " is not reachable {}", dateFormat.format(new Date()));
        return null;
    }
}
