package com.raquibul.bank.payment.rest.service.exception;

public class TransactionSaveException extends TransactionServiceException{
    private static final int ERROR_CODE = 100102;

    public TransactionSaveException() {
        super(ERROR_CODE);
    }
}
