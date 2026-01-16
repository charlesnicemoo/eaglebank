package com.eaglebank.aggregate.domain.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateAccountDTO(
        @JsonProperty String name,
        @JsonProperty String accountType
){}