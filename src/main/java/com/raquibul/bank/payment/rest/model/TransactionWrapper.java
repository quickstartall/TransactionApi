package com.raquibul.bank.payment.rest.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class TransactionWrapper {
    @NotBlank
    private String accountNumber;
    private List<TransactionDomain> transactionDomains;
}
