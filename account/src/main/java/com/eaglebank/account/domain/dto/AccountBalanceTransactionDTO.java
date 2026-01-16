package com.eaglebank.account.domain.dto;

public record AccountBalanceTransactionDTO(
        String amount,
        String currency,
        String reason
){}