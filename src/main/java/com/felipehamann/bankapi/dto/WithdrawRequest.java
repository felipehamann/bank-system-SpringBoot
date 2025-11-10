package com.felipehamann.bankapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WithdrawRequest(
        @NotBlank String accountNumber,
        @NotNull BigDecimal amount
) {}
