package com.javabootcamp.fintechbank.accounts;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransferRequest(
        @NotNull
        @Positive
        Double amount,

        String remark
) {
}
