package com.eaglebank.aggregate.domain.dto.user;

public record UserAddress(
        String line1,
        String line2,
        String line3,
        String town,
        String county,
        String postcode
) {}