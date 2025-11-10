package com.felipehamann.bankapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAccountRequest(
        @NotNull Long customerId,
        @NotBlank String currency
) {}