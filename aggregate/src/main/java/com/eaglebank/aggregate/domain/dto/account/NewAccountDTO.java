package com.eaglebank.aggregate.domain.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record NewAccountDTO(
        @NotBlank(message = "Name is required")
        @JsonProperty("name") String name,
        @NotBlank(message = "Account type is required")
        @JsonProperty("accountType") String accountType
) {}