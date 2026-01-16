package com.eaglebank.aggregate.domain.dto.account;

public record AccountDTO(
        String accountNumber,
        String sortCode,
        String name,
        String accountType,
        String balance,
        String currency,
        String createdTimestamp,
        String updatedTimestamp
){}