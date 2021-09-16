package com.raquibul.bank.payment.rest.service;

import com.raquibul.bank.payment.rest.model.TransactionWrapper;
import com.raquibul.bank.payment.rest.model.TransferOrder;
import com.raquibul.bank.payment.rest.service.exception.*;

public interface TransactionService {
    public String transferMoney(TransferOrder transferOrder) throws TransactionAccountNotFoundException, TransactionSaveException, BeneficiaryAccountNotFoundException, TransactionValidationException;

    public TransactionWrapper findTransactionsByAccount(String accountNumber) throws TransactionAccountNotFoundException, TransactionGetException;
}
