package com.felipehamann.bankapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DepositRequest(
        @NotBlank String accountNumber,
        @NotNull BigDecimal amount
) {}
