package com.eaglebank.transaction.domain.dto;

public record TransactionDTO(
        String amount,
        String currency,
        String type,
        String reference,
        String userId
){}
