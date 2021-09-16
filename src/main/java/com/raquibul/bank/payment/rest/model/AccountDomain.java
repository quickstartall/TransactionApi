package com.raquibul.bank.payment.rest.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AccountDomain {
    private Long id;

    @NotBlank
    private String accountNumber;

    @NotNull
    private AccountType accountType;

    private BigDecimal balance;
}

enum AccountType {
    SAVINGS, CURRENT
}