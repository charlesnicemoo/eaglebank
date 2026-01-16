package com.eaglebank.account.domain.dto;

public record NewAccountDTO(
        String name,
        String accountType,
        String currency
) {}