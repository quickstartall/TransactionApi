package com.raquibul.bank.payment.rest.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class TransferOrder {
    @NotNull
    private AccountDomain sourceAccountDomain;

    @NotNull
    private AccountDomain beneficiaryAccountDomain;

    @NotNull
    @Positive
    private BigDecimal transferAmount;

    @NotBlank
    private String transferDescription;
}
