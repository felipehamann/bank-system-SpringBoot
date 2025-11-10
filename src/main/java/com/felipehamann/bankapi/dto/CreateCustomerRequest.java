package com.felipehamann.bankapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateCustomerRequest(
        @NotBlank String fullName,
        @Email @NotBlank String email
) {}
