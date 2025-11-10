package com.felipehamann.bankapi.dto;

import com.felipehamann.bankapi.model.AccountStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeAccountStatusRequest(
        @NotNull AccountStatus status
) {}
