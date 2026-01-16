package com.eaglebank.aggregate.domain.dto.transaction;

import com.fasterxml.jackson.annotation.JsonValue;

public record CreateTransactionDTO(
        double amount,
        String currency,
        TransactionType type,
        String reference
) {
    public enum TransactionType {
        WITHDRAWAL("withdrawal"),
        DEPOSIT("deposit");

        private final String value;

        TransactionType(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }
    }
}