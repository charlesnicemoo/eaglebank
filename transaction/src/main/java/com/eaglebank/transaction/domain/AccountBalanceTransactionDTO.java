package com.eaglebank.transaction.domain;

public record AccountBalanceTransactionDTO(
        String amount,
        String currency,
        String type,
        String reference
){}