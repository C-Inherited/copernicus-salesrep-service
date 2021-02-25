package com.ironhack.salesrep.controller.impl;

import com.ironhack.salesrep.auth.dto.AuthenticationRequest;
import com.ironhack.salesrep.auth.dto.AuthenticationResponse;
import com.ironhack.salesrep.auth.service.MyUserDetailsService;
import com.ironhack.salesrep.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    private static String contacLeadAuthOk;
    private static String contactOpportunityAuthOk;

    /** AUTHENTICATION **/
    @PostMapping("/salesrep/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }


        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


    public static String getContacLeadAuthOk() {
        return contacLeadAuthOk;
    }

    public static void setContacLeadAuthOk(String contacLeadAuthOk) {
        AuthController.contacLeadAuthOk = contacLeadAuthOk;
    }

    public static String getContactOpportunityAuthOk() {
        return contactOpportunityAuthOk;
    }

    public static void setContactOpportunityAuthOk(String contactOpportunityAuthOk) {
        AuthController.contactOpportunityAuthOk = contactOpportunityAuthOk;
    }
}
