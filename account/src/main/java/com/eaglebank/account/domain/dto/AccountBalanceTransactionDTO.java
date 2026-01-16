package com.eaglebank.account.domain.dto;

public record AccountBalanceTransactionDTO(
        String amount,
        String accountNumber,
        String currency,
        String type,
        String reference
){}