package com.eaglebank.account.domain.dto;

public record AccountResponseDTO (
        String accountNumber,
        String sortCode,
        String name,
        String accountType,
        String balance,
        String currency,
        String createdTimestamp,
        String updatedTimestamp
){}