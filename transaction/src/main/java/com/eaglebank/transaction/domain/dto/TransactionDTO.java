package com.eaglebank.transaction.domain.dto;

public record TransactionDTO(
        String amount,
        String accountNumber,
        String currency,
        String type,
        String reference,
        String userId
){}
