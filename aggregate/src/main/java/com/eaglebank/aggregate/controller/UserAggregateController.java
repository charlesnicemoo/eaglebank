package com.eaglebank.aggregate.controller;

import com.eaglebank.aggregate.domain.dto.user.UserAddress;
import com.eaglebank.aggregate.domain.dto.user.UserResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/users")
public class UserAggregateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAggregateController.class);

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserResponseDTO user) {
        LOGGER.debug("Create user: {}", user);
        URI location = URI.create("fillLater");
        return ResponseEntity.created(location).body(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> fetchUserByID(@PathVariable String userId) {
        LOGGER.debug("Fetch user by ID: {}", userId);
        UserAddress userAddress = new UserAddress("line1","line2","line3","town","county","postcode");
        UserResponseDTO userDTO = new UserResponseDTO("id","name", userAddress, "phone","email","createTimestamp","updateTimestamp");
        return ResponseEntity.ok().body(userDTO);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUserByID(@PathVariable String userId, @RequestBody UserResponseDTO user) {
        LOGGER.debug("Update user: {}", user);
        return ResponseEntity.ok().body(user);
    }

    // TODO: Again remember to add -I to see 204 from curl
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserByID(@PathVariable String userId) {
        LOGGER.debug("Delete user by ID: {}", userId);
        return ResponseEntity.noContent().build();
    }

    // TODO: There does not appear to be any way to associate user and account in the spec? Maybe an build one out.
}