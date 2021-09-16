package com.raquibul.bank.payment.rest.service.exception;

public class TransactionValidationException extends TransactionServiceException {
    private static final int ERROR_CODE = 100105;

    public TransactionValidationException(String message) {
        super(ERROR_CODE, message);
    }
}
