package com.raquibul.bank.payment.rest.service.exception;

public class TransactionGetException extends TransactionServiceException{
    private static final int ERROR_CODE = 100103;

    public TransactionGetException() {
        super(ERROR_CODE);
    }
}
