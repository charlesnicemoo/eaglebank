package com.eaglebank.aggregate.controller;

import com.eaglebank.aggregate.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AggregateAuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregateAuthController.class);
    private final JwtService jwtService;

    public AggregateAuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    // Remember use -u user:pw in the curl
    // THEN -H "Authorization: Bearer ...token..." in followup.
    @PostMapping("/v1/token")
    public ResponseEntity<String> getToken(Authentication authentication) {
        LOGGER.debug("Get token");
        String token = jwtService.generateToken(authentication);
        return ResponseEntity.ok(token);
    }
}