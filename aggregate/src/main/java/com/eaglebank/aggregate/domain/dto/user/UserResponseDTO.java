package com.eaglebank.aggregate.domain.dto.user;

public record UserResponseDTO(
        String id,
        String name,
        UserAddress address,
        String phoneNumber,
        String email,
        String createdTimestamp,
        String updatedTimestamp
){}