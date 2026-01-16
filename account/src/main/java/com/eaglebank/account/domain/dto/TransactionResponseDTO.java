package com.eaglebank.account.domain.dto;

import java.math.BigDecimal;

public record TransactionResponseDTO(
        String id,
        BigDecimal amount,
        String currency,
        String type,
        String reference,
        String userId,
        String createdTimestamp
){}