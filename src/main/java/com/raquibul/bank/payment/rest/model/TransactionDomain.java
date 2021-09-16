package com.raquibul.bank.payment.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class TransactionDomain {
    @NotNull
    private String transactionReference;
    @PastOrPresent
    private OffsetDateTime transactionTime;
    @NotBlank
    private String transactionDescription;
    @Positive
    private BigDecimal transactionAmount;
    @NotBlank
    @JsonIgnore
    private String sourceAccountNumber;
    @NotBlank
    private String beneficiaryAccountNumber;
}
